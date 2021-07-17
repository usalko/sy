package io.github.usalko.sy.controller;

import io.github.usalko.sy.convert.OwnMoodConvertor;
import io.github.usalko.sy.convert.SharedMoodConvertor;
import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;
import io.github.usalko.sy.service.GeometryShapeService;
import io.github.usalko.sy.service.MoodGeometryShapeService;
import io.github.usalko.sy.service.MoodService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Map;

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
    public @NotNull Iterable<? extends Mood> getSharedMoods() {
        return this.moodService.getSharedMoods(50);
    }

    @GetMapping(value = {"/GetHistory"})
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<? extends Mood> getHistory(String token) {
        return this.moodService.getOwnMoods(token, 50);
    }

    @GetMapping(value = {"/KeepMoodForNow"})
    @ResponseStatus(HttpStatus.OK)
    public @NotNull void keepMoodForNow(String token, Map<String, Object> mood) {
        OwnMood ownMood = new OwnMoodConvertor(mood).convert();
        this.moodService.keep(token, ownMood);
        ownMood.getMoodGeometryShapes().forEach(s -> this.moodGeometryShapeService.create(s));
    }

    @GetMapping(value = {"/ShareMood"})
    @ResponseStatus(HttpStatus.OK)
    public @NotNull void shareMood(String token, Map<String, Object> mood) {
        SharedMood sharedMood = new SharedMoodConvertor(mood).convert();
        this.moodService.share(token, sharedMood);
        sharedMood.getMoodGeometryShapes().forEach(s -> this.moodGeometryShapeService.create(s));
    }

}