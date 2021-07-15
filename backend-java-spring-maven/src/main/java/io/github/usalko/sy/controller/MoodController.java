package io.github.usalko.sy.controller;

import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.service.GeometryShapeService;
import io.github.usalko.sy.service.MoodGeometryShapeService;
import io.github.usalko.sy.service.MoodService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class MoodController {

    GeometryShapeService geometryShapeService;
    MoodService moodService;
    MoodGeometryShapeService moodGeometryShapeService;

    public MoodController(GeometryShapeService geometryShapeService, MoodService moodService, MoodGeometryShapeService moodGeometryShapeService) {
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
        return this.moodService.getSharedMoods(50);
    }

    @GetMapping(value = {"/KeepMoodForNow"})
    @ResponseStatus(HttpStatus.OK)
    public @NotNull void keepMoodForNow(String token, Mood mood) {
        this.moodService.keep(token, mood);
    }

    @GetMapping(value = {"/ShareMood"})
    @ResponseStatus(HttpStatus.OK)
    public @NotNull void shareMood(String token, Mood mood) {
        this.moodService.share(token, mood);
    }

}