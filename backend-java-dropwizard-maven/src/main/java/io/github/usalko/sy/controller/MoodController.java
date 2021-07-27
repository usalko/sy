//// Sy (Share your mood with anyone)
//// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
////
//// This program is free software: you can redistribute it and/or modify
//// it under the terms of the GNU General Public License as published by
//// the Free Software Foundation, either version 3 of the License, or
//// (at your option) any later version.
////
//// This program is distributed in the hope that it will be useful,
//// but WITHOUT ANY WARRANTY; without even the implied warranty of
//// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//// GNU General Public License for more details.
////
//// You should have received a copy of the GNU General Public License
//// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//package io.github.usalko.sy.controller;
//
//import io.github.usalko.sy.model.*;
//import io.github.usalko.sy.service.GeometryShapeService;
//import io.github.usalko.sy.service.MoodGeometryShapeService;
//import io.github.usalko.sy.service.MoodService;
//import io.github.usalko.sy.service.TokenService;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.constraints.NotNull;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/api")
//public class MoodController {
//
//    GeometryShapeService geometryShapeService;
//    MoodService moodService;
//    MoodGeometryShapeService moodGeometryShapeService;
//    TokenService tokenService;
//
//    public MoodController(GeometryShapeService geometryShapeService,
//                          MoodService moodService,
//                          MoodGeometryShapeService moodGeometryShapeService,
//                          TokenService tokenService) {
//        this.geometryShapeService = geometryShapeService;
//        this.moodService = moodService;
//        this.moodGeometryShapeService = moodGeometryShapeService;
//        this.tokenService = tokenService;
//    }
//
//    @GetMapping(value = {"/GetSharedMoods"})
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Request all shared moods")
//    public @NotNull Iterable<SharedMood> getSharedMoods() {
//        Iterable<SharedMood> result = this.moodService.getSharedMoods(200);
//        result.forEach(this::inPlaceUnpackMoodGeometryShapes);
//        return result;
//    }
//
//    @SuppressWarnings("unchecked")
//    private <T extends MoodGeometryShape> void inPlaceUnpackMoodGeometryShapes(Mood<T> mood) {
//        if (mood.getMoodGeometryShapes().size() > 0) {
//            MoodGeometryShape[] content = mood.getMoodGeometryShapes().toArray(new MoodGeometryShape[0]);
//            Arrays.sort(content);
//            List<T> result = new ArrayList<>();
//            int cursor = 0;
//            int lastIndex = content[content.length - 1].getIndexInList();
//            for (int i = 0; i <= lastIndex; i++) {
//                if (content[cursor].getIndexInList() > i) {
//                    result.add(null);
//                    continue;
//                }
//                result.add((T) content[cursor]);
//                cursor++;
//            }
//            mood.setMoodGeometryShapes(result);
//        }
//    }
//
//    @GetMapping(value = {"/GetHistory"})
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Request your own mood history")
//    public @NotNull Iterable<OwnMood> getHistory(String token) throws IllegalAccessException {
//        Optional<Token> persistedToken = this.tokenService.getToken(token);
//        if (!persistedToken.isPresent()) {
//            throw new IllegalAccessException(String.format("Token %s is not presented in database", token));
//        }
//        Iterable<OwnMood> result = this.moodService.getOwnMoods(persistedToken.get(), 50);
//        result.forEach(this::inPlaceUnpackMoodGeometryShapes);
//        return result;
//    }
//
//    @PostMapping(value = {"/KeepMoodForNow"})
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Keep your own mood for now")
//    public @NotNull void keepMoodForNow(String token, @RequestBody OwnMood mood) throws IllegalAccessException {
//        Optional<Token> persistedToken = this.tokenService.getToken(token);
//        if (!persistedToken.isPresent()) {
//            throw new IllegalAccessException(String.format("Token %s is not presented in database", token));
//        }
//        this.geometryShapeService.restoreGeometryShapes(mood);
//        this.moodService.keep(persistedToken.get(), mood);
//        List<OwnMoodGeometryShape> moodGeometryShapes = mood.getMoodGeometryShapes();
//        IntStream.range(0, moodGeometryShapes.size()).forEach(index -> {
//            OwnMoodGeometryShape moodGeometryShape = moodGeometryShapes.get(index);
//            if (moodGeometryShape != null) {
//                // Pack geometry shapes
//                moodGeometryShape.setIndexInList(index);
//                this.moodGeometryShapeService.create(moodGeometryShape);
//            }
//        });
//    }
//
//    @PostMapping(value = {"/ShareMood"})
//    @ResponseStatus(HttpStatus.OK)
//    @ApiOperation(value = "Share mood for everyone")
//    public @NotNull void shareMood(String token, @RequestBody SharedMood mood) throws IllegalAccessException {
//        Optional<Token> persistedToken = this.tokenService.getToken(token);
//        if (!persistedToken.isPresent()) {
//            throw new IllegalAccessException(String.format("Token %s is not presented in database", token));
//        }
//        this.geometryShapeService.restoreGeometryShapes(mood);
//        this.moodService.share(mood);
//        List<SharedMoodGeometryShape> moodGeometryShapes = mood.getMoodGeometryShapes();
//        IntStream.range(0, moodGeometryShapes.size()).forEach(index -> {
//            SharedMoodGeometryShape moodGeometryShape = moodGeometryShapes.get(index);
//            if (moodGeometryShape != null) {
//                // Pack geometry shapes
//                moodGeometryShape.setIndexInList(index);
//                this.moodGeometryShapeService.create(moodGeometryShape);
//            }
//        });
//    }
//
//}