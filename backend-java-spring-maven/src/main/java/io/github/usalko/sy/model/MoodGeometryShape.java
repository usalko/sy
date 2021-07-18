package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class MoodGeometryShape implements Comparable<MoodGeometryShape> {

    @Column(nullable = false)
    private Integer color;

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public abstract Integer getIndex();

    @Transient
    @JsonIgnore
    public abstract void setIndex(Integer index);

    @Transient
    @JsonIgnore
    public abstract void setMood(Mood<? extends MoodGeometryShape> mood);

    public abstract GeometryShape getGeometryShape();

    @Transient
    public abstract void setGeometryShape(GeometryShape geometryShape);

    @Override
    public int compareTo(MoodGeometryShape o) {
        return Integer.compare(this.getIndex(), o.getIndex());
    }
}
