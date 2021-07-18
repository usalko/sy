package io.github.usalko.sy.controller;

import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;
import io.github.usalko.sy.service.GeometryShapeService;
import io.github.usalko.sy.service.MoodGeometryShapeService;
import io.github.usalko.sy.service.MoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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
    public @NotNull Iterable<? extends Mood> getSharedMoods() {
        return this.moodService.getSharedMoods(50);
    }

    @GetMapping(value = {"/GetHistory"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Request your own mood history")
    public @NotNull Iterable<? extends Mood> getHistory(String token) {
        return this.moodService.getOwnMoods(token, 50);
    }

    @PostMapping(value = {"/KeepMoodForNow"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Keep your own mood for now")
    public @NotNull void keepMoodForNow(String token, @RequestBody OwnMood mood) {
        this.moodService.keep(token, mood);
        mood.getMoodGeometryShapes().forEach(s -> this.moodGeometryShapeService.create(s));
    }

    @PostMapping(value = {"/ShareMood"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Share mood for everyone")
    public @NotNull void shareMood(String token, @RequestBody SharedMood mood) {
        this.moodService.share(token, mood);
        mood.getMoodGeometryShapes().forEach(s -> this.moodGeometryShapeService.create(s));
    }

}