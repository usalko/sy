package io.github.usalko.sy.controller;

import io.github.usalko.sy.model.*;
import io.github.usalko.sy.service.GeometryShapeService;
import io.github.usalko.sy.service.MoodGeometryShapeService;
import io.github.usalko.sy.service.MoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class MoodController {

    GeometryShapeService geometryShapeService;
    MoodService moodService;
    MoodGeometryShapeService moodGeometryShapeService;

    public MoodController(GeometryShapeService geometryShapeService,
                          MoodService moodService,
                          MoodGeometryShapeService moodGeometryShapeService) {
        this.geometryShapeService = geometryShapeService;
        this.moodService = moodService;
        this.moodGeometryShapeService = moodGeometryShapeService;
    }

    @GetMapping(value = {"/GetSharedMoods"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Request all shared moods")
    public @NotNull Iterable<SharedMood> getSharedMoods() {
        Iterable<SharedMood> result = this.moodService.getSharedMoods(50);
        result.forEach(this::inPlaceUnpackMoodGeometryShapes);
        return result;
    }

    @SuppressWarnings("unchecked")
    private <T extends MoodGeometryShape> void inPlaceUnpackMoodGeometryShapes(Mood<T> mood) {
        if (mood.getMoodGeometryShapes().size() > 0) {
            MoodGeometryShape[] content = mood.getMoodGeometryShapes().toArray(new MoodGeometryShape[0]);
            Arrays.sort(content);
            List<T> result = new ArrayList<>();
            int cursor = 0;
            int lastIndex = content[content.length - 1].getIndex();
            for (int i = 0; i <= lastIndex; i++) {
                if (content[cursor].getIndex() > i) {
                    result.add(null);
                    continue;
                }
                result.add((T) content[cursor]);
                cursor++;
            }
            mood.setMoodGeometryShapes(result);
        }
    }

    @GetMapping(value = {"/GetHistory"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Request your own mood history")
    public @NotNull Iterable<OwnMood> getHistory(String token) {
        Iterable<OwnMood> result = this.moodService.getOwnMoods(token, 50);
        result.forEach(this::inPlaceUnpackMoodGeometryShapes);
        return result;
    }

    @PostMapping(value = {"/KeepMoodForNow"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Keep your own mood for now")
    public @NotNull void keepMoodForNow(String token, @RequestBody OwnMood mood) {
        this.geometryShapeService.restoreGeometryShapes(mood);
        this.moodService.keep(token, mood);
        List<OwnMoodGeometryShape> moodGeometryShapes = mood.getMoodGeometryShapes();
        IntStream.range(0, moodGeometryShapes.size()).forEach(index -> {
            OwnMoodGeometryShape moodGeometryShape = moodGeometryShapes.get(index);
            if (moodGeometryShape != null) {
                // Pack geometry shapes
                moodGeometryShape.setIndex(index);
                this.moodGeometryShapeService.create(moodGeometryShape);
            }
        });
    }

    @PostMapping(value = {"/ShareMood"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Share mood for everyone")
    public @NotNull void shareMood(String token, @RequestBody SharedMood mood) {
        this.geometryShapeService.restoreGeometryShapes(mood);
        this.moodService.share(token, mood);
        List<SharedMoodGeometryShape> moodGeometryShapes = mood.getMoodGeometryShapes();
        IntStream.range(0, moodGeometryShapes.size()).forEach(index -> {
            SharedMoodGeometryShape moodGeometryShape = moodGeometryShapes.get(index);
            if (moodGeometryShape != null) {
                // Pack geometry shapes
                moodGeometryShape.setIndex(index);
                this.moodGeometryShapeService.create(moodGeometryShape);
            }
        });
    }

}