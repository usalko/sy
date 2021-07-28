package io.github.usalko.sy.resources;

import io.github.usalko.sy.db.GeometryShapeDao;
import io.github.usalko.sy.db.MoodDao;
import io.github.usalko.sy.db.TokenDao;
import io.github.usalko.sy.domain.GeometryShape;
import io.github.usalko.sy.domain.Mood;
import io.github.usalko.sy.domain.MoodGeometryShape;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MoodResource {

    private final GeometryShapeDao geometryShapeDao;
    private final TokenDao tokenDao;
    private final MoodDao moodDao;

    @Inject
    public MoodResource(TokenDao tokenDao, MoodDao moodDao, GeometryShapeDao geometryShapeDao) {
        this.tokenDao = tokenDao;
        this.moodDao = moodDao;
        this.geometryShapeDao = geometryShapeDao;
    }

    @GET
    @Path("/GetHistory")
    public List<Mood> getHistory(@QueryParam("token") @NotNull String token)
            throws IllegalAccessException {
        if (!tokenDao.exists(token)) {
            throw new IllegalAccessException(
                    String.format("Token %s is not presented in the database", token));
        }
        return moodDao.findTopOwnMoodsSortedByIdDesc(token, 50);
    }

    @GET
    @Path("/GetSharedMoods")
    public List<Mood> getSharedMoods() {
        return moodDao.findTopSharedMoodsSortedByIdDesc(200);
    }

    @POST
    @Path("/KeepMoodForNow")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveOwnMood(@QueryParam("token") @NotNull String token, @NotNull Mood mood)
            throws IllegalAccessException {
        if (!tokenDao.exists(token)) {
            throw new IllegalAccessException(
                    String.format("Token %s is not presented in the database", token));
        }
        Map<String, Long> shapes = geometryShapeDao.findAll().stream().collect(Collectors.toMap(
                GeometryShape::getMnemonic, GeometryShape::getId));
        moodDao.inTransaction(transactional -> {
            if (mood.getCreated() == null) {
                mood.setCreated(LocalDateTime.now());
            }
            mood.getShape().setId(shapes.get(mood.getShape().getMnemonic()));
            long ownMoodId = transactional.saveOwnMood(mood);
            MoodGeometryShape[] content = mood.getContent().toArray(new MoodGeometryShape[0]);
            List<MoodGeometryShape> valuableContent = IntStream
                    .range(0, content.length)
                    .filter(i -> content[i] != null)
                    .mapToObj(i -> {
                        long geometryShapeId = shapes.get(content[i].getShape().getMnemonic());
                        content[i].setIndexInList(i);
                        content[i].setOwnMoodId(ownMoodId);
                        content[i].getShape()
                                .setId(geometryShapeId);
                        content[i].setGeometryShapeId(geometryShapeId);
                        return content[i];
                    })
                    .collect(Collectors.toList());
            transactional.saveOwnMoodGeometryShapes(valuableContent);
            transactional.saveTokenOwnMood(token, ownMoodId);
            return null;
        });
    }


}