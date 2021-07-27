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
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import io.swagger.annotations.ApiModelProperty;
//
//import javax.persistence.EmbeddedId;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.util.Objects;
//
//@Entity
//@Table(name = "shared_mood_geometry_shapes")
//public class SharedMoodGeometryShape extends MoodGeometryShape {
//    @EmbeddedId
//    @JsonIgnore
//    @ApiModelProperty(hidden = true)
//    private SharedMoodGeometryShapePK pk;
//
//    public SharedMoodGeometryShape() {
//        super();
//        pk = new SharedMoodGeometryShapePK();
//    }
//
//    public SharedMoodGeometryShape(SharedMood sharedMood, GeometryShape geometry, Integer color, Integer indexInList) {
//        pk = new SharedMoodGeometryShapePK();
//        pk.setSharedMood(sharedMood);
//        pk.setGeometryShape(geometry);
//        this.setColor(color);
//        this.setIndexInList(indexInList);
//    }
//
//    @Override
//    public Integer getIndexInList() {
//        return this.pk.getIndexInList();
//    }
//
//    @Override
//    public void setIndexInList(Integer indexInList) {
//        this.pk.setIndexInList(indexInList);
//    }
//
//    @Override
//    public void setMood(Mood<? extends MoodGeometryShape> mood) {
//        this.pk.setSharedMood((SharedMood) mood);
//    }
//
//    public GeometryShape getGeometryShape() {
//        return this.pk.getGeometryShape();
//    }
//
//    @Override
//    public void setGeometryShape(GeometryShape geometryShape) {
//        this.pk.setGeometryShape(geometryShape);
//    }
//
//    public SharedMoodGeometryShapePK getPk() {
//        return pk;
//    }
//
//    public void setPk(SharedMoodGeometryShapePK pk) {
//        this.pk = pk;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        SharedMoodGeometryShape that = (SharedMoodGeometryShape) o;
//        return Objects.equals(pk, that.pk) &&
//                Objects.equals(getColor(), that.getColor());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(pk, getColor());
//    }
//}
