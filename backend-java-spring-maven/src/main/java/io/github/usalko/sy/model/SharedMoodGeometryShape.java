package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "shared_mood_geometry_shape")
public class SharedMoodGeometryShape extends MoodGeometryShape {
    @EmbeddedId
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private SharedMoodGeometryShapePK pk;

    public SharedMoodGeometryShape() {
        super();
        pk = new SharedMoodGeometryShapePK();
    }

    public SharedMoodGeometryShape(SharedMood sharedMood, GeometryShape geometry, Integer color, Integer index) {
        pk = new SharedMoodGeometryShapePK();
        pk.setSharedMood(sharedMood);
        pk.setGeometryShape(geometry);
        this.setColor(color);
        this.setIndex(index);
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
    public void setMood(Mood<? extends MoodGeometryShape> mood) {
        this.pk.setSharedMood((SharedMood) mood);
    }

    public GeometryShape getGeometryShape() {
        return this.pk.getGeometryShape();
    }

    @Override
    public void setGeometryShape(GeometryShape geometryShape) {
        this.pk.setGeometryShape(geometryShape);
    }

    public SharedMoodGeometryShapePK getPk() {
        return pk;
    }

    public void setPk(SharedMoodGeometryShapePK pk) {
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
        SharedMoodGeometryShape that = (SharedMoodGeometryShape) o;
        return Objects.equals(pk, that.pk) &&
                Objects.equals(getColor(), that.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, getColor());
    }
}
