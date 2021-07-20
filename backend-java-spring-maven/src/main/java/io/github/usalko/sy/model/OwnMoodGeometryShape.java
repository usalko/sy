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

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "own_mood_geometry_shape")
public class OwnMoodGeometryShape extends MoodGeometryShape {
    @EmbeddedId
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private OwnMoodGeometryShapePK pk;

    public OwnMoodGeometryShape() {
        super();
        pk = new OwnMoodGeometryShapePK();
    }

    public OwnMoodGeometryShape(OwnMood ownMood, GeometryShape geometry, Integer color, Integer index) {
        pk = new OwnMoodGeometryShapePK();
        pk.setOwnMood(ownMood);
        pk.setGeometryShape(geometry);
        this.setColor(color);
        pk.setIndex(index);
    }

    @Override
    public void setMood(Mood<? extends MoodGeometryShape> mood) {
        this.pk.setOwnMood((OwnMood) mood);
    }

    @Override
    public Integer getIndex() {
        return this.pk.getIndex();
    }

    @Override
    public void setIndex(Integer index) {
        this.pk.setIndex(index);
    }

    @Override
    public GeometryShape getGeometryShape() {
        return this.pk.getGeometryShape();
    }

    @Override
    public void setGeometryShape(GeometryShape geometryShape) {
        this.pk.setGeometryShape(geometryShape);
    }

    public OwnMoodGeometryShapePK getPk() {
        return pk;
    }

    public void setPk(OwnMoodGeometryShapePK pk) {
        this.pk = pk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OwnMoodGeometryShape that = (OwnMoodGeometryShape) o;
        return Objects.equals(pk, that.pk) &&
                Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, getColor());
    }
}
