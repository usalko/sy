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
//package io.github.usalko.sy.model;
//
//import javax.persistence.Entity;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//import javax.validation.Valid;
//import javax.validation.constraints.NotNull;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "shared_moods")
//public class SharedMood extends Mood<SharedMoodGeometryShape> {
//
//    @OneToMany(mappedBy = "pk.sharedMood")
//    @Valid
//    private List<SharedMoodGeometryShape> moodGeometryShapes = new ArrayList<>();
//
//    public SharedMood(Long id,
//                      @NotNull(message = "Parent geometry form is required.") GeometryShape geometryShape) {
//        this.setId(id);
//        this.setGeometryShape(geometryShape);
//    }
//
//    public SharedMood() {
//    }
//
//    @Override
//    public List<SharedMoodGeometryShape> getMoodGeometryShapes() {
//        return moodGeometryShapes;
//    }
//
//    public void setMoodGeometryShapes(List<SharedMoodGeometryShape> sharedMoodGeometryShapes) {
//        this.moodGeometryShapes = sharedMoodGeometryShapes;
//    }
//
//}
