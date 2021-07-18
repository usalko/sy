package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "shared_mood_geometry_shape")
public class SharedMoodGeometryShape extends MoodGeometryShape {
    @EmbeddedId
    @JsonIgnore
    @ApiModelProperty(hidden = true)
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
