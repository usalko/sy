package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "own_mood_geometry_shape")
public class OwnMoodGeometryShape extends MoodGeometryShape {
    @EmbeddedId
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private OwnMoodGeometryShapePK pk;

    @Column(nullable = false)
    private Integer color;

    public OwnMoodGeometryShape() {
        super();
    }

    public OwnMoodGeometryShape(OwnMood ownMood, GeometryShape geometry, Integer color) {
        pk = new OwnMoodGeometryShapePK();
        pk.setOwnMood(ownMood);
        pk.setGeometryShape(geometry);
        this.color = color;
    }

    @Transient
    public GeometryShape getGeometryShape() {
        return this.pk.getGeometryShape();
    }

    public OwnMoodGeometryShapePK getPk() {
        return pk;
    }

    public void setPk(OwnMoodGeometryShapePK pk) {
        this.pk = pk;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
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
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, color);
    }
}
