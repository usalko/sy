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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "geometry_shape_id")
    private GeometryShape geometryShape;

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
                Objects.equals(geometryShape, that.geometryShape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownMood, geometryShape);
    }
}