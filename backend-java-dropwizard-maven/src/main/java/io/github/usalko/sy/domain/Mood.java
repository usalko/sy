package io.github.usalko.sy.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.Setter;
import org.jdbi.v3.core.mapper.Nested;

@Data
public class Mood {

    String id;

    LocalDateTime created;

    @NotNull
    @JsonProperty
    @Setter(onMethod = @__(@Nested()))
    GeometryShape shape;

    @NotNull
    @JsonProperty
    @Setter(onMethod = @__(@Nested()))
    List<MoodGeometryShape> content;

}
