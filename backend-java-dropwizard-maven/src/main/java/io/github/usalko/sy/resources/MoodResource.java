package io.github.usalko.sy.resources;

import io.github.usalko.sy.db.GeometryShapeDao;
import io.github.usalko.sy.db.MoodDao;
import io.github.usalko.sy.db.TokenDao;
import io.github.usalko.sy.domain.GeometryShape;
import io.github.usalko.sy.domain.Mood;
import io.github.usalko.sy.domain.MoodGeometryShape;
import io.github.usalko.sy.utils.SomeCollectors;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MoodResource {

    private final GeometryShapeDao geometryShapeDao;
    private final TokenDao tokenDao;
    private final MoodDao moodDao;

    public MoodResource(TokenDao tokenDao, MoodDao moodDao, GeometryShapeDao geometryShapeDao) {
        this.tokenDao = tokenDao;
        this.moodDao = moodDao;
        this.geometryShapeDao = geometryShapeDao;
    }

    private void validateTokenAndThrowExceptionIfInvalid(@QueryParam("token") @NotNull String token)
            throws IllegalAccessException {
        if (!tokenDao.exists(token)) {
            throw new IllegalAccessException(
                    String.format("Token %s is not presented in the database", token));
        }
    }

    private List<MoodGeometryShape> packMoodGeometryShapes(Mood mood, Map<String, Long> shapes,
            long moodId) {
        MoodGeometryShape[] content = mood.getContent().toArray(new MoodGeometryShape[0]);
        return IntStream
                .range(0, content.length)
                .filter(i -> content[i] != null)
                .mapToObj(i -> {
                    long geometryShapeId = shapes.get(content[i].getShape().getMnemonic());
                    content[i].setIndexInList(i);
                    content[i].setMoodId(moodId);
                    content[i].getShape()
                            .setId(geometryShapeId);
                    content[i].setGeometryShapeId(geometryShapeId);
                    return content[i];
                })
                .collect(Collectors.toList());
    }

    private Map<String, Long> getGeometryShapesIndex() {
        return geometryShapeDao.findAll().stream().collect(Collectors.toMap(
                GeometryShape::getMnemonic, GeometryShape::getId));
    }

    private Map<Long, GeometryShape> getGeometryShapes() {
        return geometryShapeDao.findAll().stream().collect(Collectors.toMap(
                GeometryShape::getId, Function.identity()));
    }


    private List<MoodGeometryShape> unpackContent(MoodGeometryShape[] content,
            Map<Long, GeometryShape> shapes) {
        List<MoodGeometryShape> result = new ArrayList<>();
        int cursor = 0;
        int lastIndex = content[content.length - 1].getIndexInList();
        for (int i = 0; i <= lastIndex; i++) {
            if (content[cursor].getIndexInList() > i) {
                result.add(null);
                continue;
            }
            content[cursor].setShape(shapes.get(content[cursor].getGeometryShapeId()));
            result.add(content[cursor]);
            cursor++;
        }
        return result;
    }

    @GET
    @Path("/GetHistory")
    public Iterable<Mood> getHistory(@QueryParam("token") @NotNull String token)
            throws IllegalAccessException {
        this.validateTokenAndThrowExceptionIfInvalid(token);
        Map<Long, GeometryShape> shapes = this.getGeometryShapes();
        Map<Long, Mood> result = moodDao.findTopOwnMoodsSortedByIdDesc(token, 50).stream()
                .collect(SomeCollectors.toLinkedMap(Mood::getId, Function.identity()));
        if (!result.isEmpty()) {
            moodDao.findOwnMoodGeometryShapesByMoodIds(result.keySet()).stream().collect(
                    Collectors.groupingBy(MoodGeometryShape::getMoodId,
                            Collectors.toList())).forEach((moodId, content) -> {
                Mood mood = result.get(moodId);
                mood.setContent(
                        this.unpackContent(content.toArray(new MoodGeometryShape[0]), shapes));
                mood.setShape(shapes.get(mood.getGeometryShapeId()));
            });
        }
        return result.values();
    }

    @GET
    @Path("/GetSharedMoods")
    public Iterable<Mood> getSharedMoods() {
        Map<Long, GeometryShape> shapes = this.getGeometryShapes();
        Map<Long, Mood> result = moodDao.findTopSharedMoodsSortedByIdDesc(200).stream()
                .collect(SomeCollectors.toLinkedMap(Mood::getId, Function.identity()));
        if (!result.isEmpty()) {
            moodDao.findSharedMoodGeometryShapesByMoodIds(result.keySet()).stream().collect(
                    Collectors.groupingBy(MoodGeometryShape::getMoodId,
                            Collectors.toList())).forEach((moodId, content) -> {
                Mood mood = result.get(moodId);
                mood.setContent(
                        this.unpackContent(content.toArray(new MoodGeometryShape[0]), shapes));
                mood.setShape(shapes.get(mood.getGeometryShapeId()));
            });
        }
        return result.values();
    }

    @POST
    @Path("/KeepMoodForNow")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveOwnMood(@QueryParam("token") @NotNull String token, @NotNull Mood mood)
            throws IllegalAccessException {
        validateTokenAndThrowExceptionIfInvalid(token);
        Map<String, Long> shapes = getGeometryShapesIndex();
        moodDao.inTransaction(transactional -> {
            if (mood.getCreated() == null) {
                mood.setCreated(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
            }
            long ownMoodId = transactional.saveOwnMood(mood.getCreated(),
                    shapes.get(mood.getShape().getMnemonic()));
            List<MoodGeometryShape> valuableContent = packMoodGeometryShapes(
                    mood, shapes, ownMoodId);
            if (!valuableContent.isEmpty()) {
                transactional.saveOwnMoodGeometryShapes(valuableContent);
            }
            transactional.saveTokenOwnMood(token, ownMoodId);
            return null;
        });
        return Response.ok().build();
    }

    @POST
    @Path("/ShareMood")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response shareMood(@QueryParam("token") @NotNull String token, @NotNull Mood mood)
            throws IllegalAccessException {
        validateTokenAndThrowExceptionIfInvalid(token);
        Map<String, Long> shapes = getGeometryShapesIndex();
        moodDao.inTransaction(transactional -> {
            if (mood.getCreated() == null) {
                mood.setCreated(DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()));
            }
            long ownMoodId = transactional.saveSharedMood(mood.getCreated(),
                    shapes.get(mood.getShape().getMnemonic()));
            List<MoodGeometryShape> valuableContent = packMoodGeometryShapes(
                    mood, shapes, ownMoodId);
            if (!valuableContent.isEmpty()) {
                transactional.saveSharedMoodGeometryShapes(valuableContent);
            }
            return null;
        });
        return Response.ok().build();
    }


}