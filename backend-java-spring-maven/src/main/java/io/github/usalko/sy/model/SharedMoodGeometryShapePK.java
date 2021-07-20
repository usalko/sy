// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "sharedMood")
public class SharedMoodGeometryShapePK implements Serializable {

    private static final long serialVersionUID = 5093828689028487784L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_mood_id")
    private SharedMood sharedMood;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "geometry_shape_id")
    private GeometryShape geometryShape;

    @Column(name = "index_in_list")
    private Integer indexInList;

    public SharedMood getSharedMood() {
        return sharedMood;
    }

    public void setSharedMood(SharedMood sharedMood) {
        this.sharedMood = sharedMood;
    }

    public GeometryShape getGeometryShape() {
        return geometryShape;
    }

    public void setGeometryShape(GeometryShape geometryShape) {
        this.geometryShape = geometryShape;
    }

    public Integer getIndexInList() {
        return indexInList;
    }

    public void setIndexInList(Integer indexInList) {
        this.indexInList = indexInList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SharedMoodGeometryShapePK that = (SharedMoodGeometryShapePK) o;
        return Objects.equals(sharedMood, that.sharedMood) &&
                Objects.equals(geometryShape, that.geometryShape) &&
                Objects.equals(indexInList, that.indexInList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sharedMood, geometryShape, indexInList);
    }
}