package io.github.usalko.sy.service;

import io.github.usalko.sy.model.GeometryShape;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public interface GeometryShapeService {
    @NotNull Iterable<GeometryShape> getAllGeometryShapes();

    GeometryShape getGeometryShape(@Min(value = 1L, message = "Invalid geometry shape ID.") long id);

    GeometryShape save(GeometryShape geometryShape);
}