package io.github.usalko.sy.service;

import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.model.MoodGeometryShape;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public interface GeometryShapeService {
    @NotNull Iterable<GeometryShape> getAllGeometryShapes();

    GeometryShape getGeometryShape(@Min(value = 1L, message = "Invalid geometry shape ID.") long id);

    void save(GeometryShape geometryShape);

    <T extends MoodGeometryShape> void restoreGeometryShapes(Mood<T> mood);
}