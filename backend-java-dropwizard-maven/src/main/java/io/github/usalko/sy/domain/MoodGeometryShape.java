package io.github.usalko.sy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoodGeometryShape {

    @JsonIgnore
    int indexInList;

    int color;

    @JsonIgnore
    long moodId;

    @NotNull
    @JsonProperty
    GeometryShape shape;

    @JsonIgnore
    long geometryShapeId;

}
