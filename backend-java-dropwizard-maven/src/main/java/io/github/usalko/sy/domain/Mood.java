package io.github.usalko.sy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Mood {

    Long id;

    @JsonProperty
    String created;

    @JsonIgnore
    Long geometryShapeId;

    @NotNull
    @JsonProperty
    GeometryShape shape;

    @NotNull
    @JsonProperty
    List<MoodGeometryShape> content;

}
