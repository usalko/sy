package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
public class SharedMoodGeometryShape extends MoodGeometryShape {
    @EmbeddedId
    @JsonIgnore
    private SharedMoodGeometryShapePK pk;

    @Column(nullable = false)
    private Integer color;

    public SharedMoodGeometryShape() {
        super();
    }

    public SharedMoodGeometryShape(SharedMood sharedMood, GeometryShape geometry, Integer color) {
        pk = new SharedMoodGeometryShapePK();
        pk.setSharedMood(sharedMood);
        pk.setGeometryShape(geometry);
        this.color = color;
    }

    @Transient
    public GeometryShape getGeometryShape() {
        return this.pk.getGeometryShape();
    }

    public SharedMoodGeometryShapePK getPk() {
        return pk;
    }

    public void setPk(SharedMoodGeometryShapePK pk) {
        this.pk = pk;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }


}
