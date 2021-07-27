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
//package io.github.usalko.sy.service;
//
//import io.github.usalko.sy.exception.ResourceNotFoundException;
//import io.github.usalko.sy.model.GeometryShape;
//import io.github.usalko.sy.model.Mood;
//import io.github.usalko.sy.model.MoodGeometryShape;
//import io.github.usalko.sy.repository.GeometryShapeRepository;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class GeometryShapeServiceImpl implements GeometryShapeService {
//
//    private final GeometryShapeRepository geometryShapeRepository;
//
//    public GeometryShapeServiceImpl(GeometryShapeRepository GeometryShapeRepository) {
//        this.geometryShapeRepository = GeometryShapeRepository;
//    }
//
//    @Override
//    public Iterable<GeometryShape> getAllGeometryShapes() {
//        return this.geometryShapeRepository.findAll();
//    }
//
//    @Override
//    public GeometryShape getGeometryShape(long id) {
//        return this.geometryShapeRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("GeometryShape not found"));
//    }
//
//    @Override
//    public void save(GeometryShape GeometryShape) {
//        this.geometryShapeRepository.save(GeometryShape);
//    }
//
//    @Override
//    public <T extends MoodGeometryShape> void restoreGeometryShapes(Mood<T> mood) {
//        GeometryShape shape = mood.getGeometryShape();
//        this.geometryShapeRepository.findGeometryShapeByMnemonic(shape.getMnemonic()).stream().findFirst()
//                .ifPresent(geometryShape -> shape.setId(geometryShape.getId()));
//        mood.getMoodGeometryShapes().forEach(e -> {
//            if (e != null) {
//                GeometryShape moodGeometryShape = e.getGeometryShape();
//                this.geometryShapeRepository.findGeometryShapeByMnemonic(moodGeometryShape.getMnemonic()).stream().findFirst()
//                        .ifPresent(geometryShape -> moodGeometryShape.setId(geometryShape.getId()));
//                e.setMood(mood);
//            }
//        });
//    }
//}
//
