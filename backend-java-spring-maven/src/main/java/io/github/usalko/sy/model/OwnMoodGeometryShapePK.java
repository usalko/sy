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

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "ownMood")
public class OwnMoodGeometryShapePK implements Serializable {

    private static final long serialVersionUID = 2905877973939653593L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "own_mood_id")
    private OwnMood ownMood;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "geometry_shape_id")
    private GeometryShape geometryShape;

    private Integer index;

    public OwnMood getOwnMood() {
        return ownMood;
    }

    public void setOwnMood(OwnMood ownMood) {
        this.ownMood = ownMood;
    }

    public GeometryShape getGeometryShape() {
        return geometryShape;
    }

    public void setGeometryShape(GeometryShape geometryShape) {
        this.geometryShape = geometryShape;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OwnMoodGeometryShapePK that = (OwnMoodGeometryShapePK) o;
        return Objects.equals(ownMood, that.ownMood) &&
                Objects.equals(geometryShape, that.geometryShape) &&
                Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownMood, geometryShape, index);
    }
}