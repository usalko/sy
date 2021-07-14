package io.github.usalko.sy.controller;

import io.github.usalko.sy.dto.MoodGeometryShapeDto;
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
import java.util.List;

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

    @GetMapping(name = "GetSharedMoods")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<Mood> getSharedMoods() {
        return this.moodService.getAllMoods();
    }

    @GetMapping(name = "GetHistory")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull Iterable<Mood> getHistory(String token) {
        return this.moodService.getAllMoods();
    }

    @GetMapping(name = "KeepMoodForNow")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull boolean keepMoodForNow(String token, Mood mood) {
        return false;
    }

    @GetMapping(name = "ShareMood")
    @ResponseStatus(HttpStatus.OK)
    public @NotNull boolean shareMood(String token, Mood mood) {
        return false;
    }

//    @GetMapping
//    public ResponseEntity<Mood> create(@RequestBody MoodForm form) {
//        List<MoodGeometryShapeDto> formDtos = form.getGeometryShapeMoods();
//        validateGeometryShapesExistence(formDtos);
//        Mood mood = new Mood();
//        mood.setStatus(MoodStatus.PAID.name());
//        mood = this.moodService.create(mood);
//
//        List<MoodGeometry> moodGeometryShapes = new ArrayList<>();
//        for (MoodGeometryDto dto : formDtos) {
//            moodGeometryShapes.add(moodGeometryShapeService.create(new MoodGeometryShape(mood, geometryShapeService.getGeometryShape(dto
//                    .getGeometryShape()
//                    .getId()), dto.getQuantity())));
//        }
//
//        mood.setMoodGeometryShapes(moodGeometryShapes);
//
//        this.moodService.update(mood);
//
//        String uri = ServletUriComponentsBuilder
//                .fromCurrentServletMapping()
//                .path("/moods/{id}")
//                .buildAndExpand(mood.getId())
//                .toString();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", uri);
//
//        return new ResponseEntity<>(mood, headers, HttpStatus.CREATED);
//    }
//
//    private void validateGeometryShapesExistence(List<MoodGeometryShapeDto> moodGeometryShapes) {
//        List<MoodGeometryShapeDto> list = moodGeometryShapes
//                .stream()
//                .filter(op -> Objects.isNull(geometryShapeService.getGeometryShape(op
//                        .getGeometryShape()
//                        .getId())))
//                .collect(Collectors.toList());
//
//        if (!CollectionUtils.isEmpty(list)) {
//            new ResourceNotFoundException("GeometryShape not found");
//        }
//    }
//
//    public static class MoodForm {
//
//        private List<MoodGeometryShapeDto> geometryShapeMoods;
//
//        public List<MoodGeometryShapeDto> getGeometryShapeMoods() {
//            return geometryShapeMoods;
//        }
//
//        public void setGeometryShapeMoods(List<MoodGeometryShapeDto> geometryShapeMoods) {
//            this.geometryShapeMoods = geometryShapeMoods;
//        }
//    }
}