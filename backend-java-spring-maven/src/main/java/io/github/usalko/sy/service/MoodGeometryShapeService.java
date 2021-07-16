package io.github.usalko.sy.service;

import io.github.usalko.sy.model.OwnMoodGeometryShape;
import io.github.usalko.sy.model.SharedMoodGeometryShape;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface MoodGeometryShapeService {

    OwnMoodGeometryShape create(@NotNull(message = "The geometry shapes for mood cannot be null.") @Valid OwnMoodGeometryShape ownMoodGeometryShape);

    SharedMoodGeometryShape create(@NotNull(message = "The geometry shapes for mood cannot be null.") @Valid SharedMoodGeometryShape sharedMoodGeometryShape);

}