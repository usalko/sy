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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "mood")
public class MoodGeometryShapePK implements Serializable {

    private static final long serialVersionUID = 2905877973939653593L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "mood_id")
    private Mood mood;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "geometry_shape_id")
    private GeometryShape geometryShape;

    public Mood getMood() {
        return mood;
    }

    public void setMood(Mood mood) {
        this.mood = mood;
    }

    public GeometryShape getGeometryShape() {
        return geometryShape;
    }

    public void setGeometryShape(GeometryShape geometryShape) {
        this.geometryShape = geometryShape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MoodGeometryShapePK that = (MoodGeometryShapePK) o;
        return mood.equals(that.mood) && geometryShape.equals(that.geometryShape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mood, geometryShape);
    }
}