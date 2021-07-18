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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "sharedMood")
public class SharedMoodGeometryShapePK implements Serializable {

    private static final long serialVersionUID = 5093828689028487784L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "shared_mood_id")
    private SharedMood sharedMood;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "geometry_shape_id")
    private GeometryShape geometryShape;

    private Integer index;

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
        SharedMoodGeometryShapePK that = (SharedMoodGeometryShapePK) o;
        return Objects.equals(sharedMood, that.sharedMood) &&
                Objects.equals(geometryShape, that.geometryShape) &&
                Objects.equals(index, that.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sharedMood, geometryShape, index);
    }
}