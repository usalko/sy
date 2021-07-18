package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
public abstract class Mood<T extends MoodGeometryShape> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;

    @NotNull(message = "Parent geometry form is required.")
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "geometry_shape_id")
    private GeometryShape geometryShape;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public GeometryShape getGeometryShape() {
        return geometryShape;
    }

    public void setGeometryShape(GeometryShape geometryShape) {
        this.geometryShape = geometryShape;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public abstract List<T> getMoodGeometryShapes();

    public abstract void setMoodGeometryShapes(List<T> result);
}
