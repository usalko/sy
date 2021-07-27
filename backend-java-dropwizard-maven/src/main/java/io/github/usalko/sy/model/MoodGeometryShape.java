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
//
//import javax.persistence.Column;
//import javax.persistence.MappedSuperclass;
//import javax.persistence.Transient;
//
//@MappedSuperclass
//public abstract class MoodGeometryShape implements Comparable<MoodGeometryShape> {
//
//    @Column(nullable = false)
//    private Integer color;
//
//    public Integer getColor() {
//        return color;
//    }
//
//    public void setColor(Integer color) {
//        this.color = color;
//    }
//
//    public abstract Integer getIndexInList();
//
//    @Transient
//    @JsonIgnore
//    public abstract void setIndexInList(Integer indexInList);
//
//    @Transient
//    @JsonIgnore
//    public abstract void setMood(Mood<? extends MoodGeometryShape> mood);
//
//    public abstract GeometryShape getGeometryShape();
//
//    @Transient
//    public abstract void setGeometryShape(GeometryShape geometryShape);
//
//    @Override
//    public int compareTo(MoodGeometryShape o) {
//        return Integer.compare(this.getIndexInList(), o.getIndexInList());
//    }
//}
