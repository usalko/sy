package io.github.usalko.sy.service;

import io.github.usalko.sy.model.MoodGeometryShape;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface MoodGeometryShapeService {

    MoodGeometryShape create(@NotNull(message = "The geometry shapes for mood cannot be null.") @Valid MoodGeometryShape orderGeometryShape);
}