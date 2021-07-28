package io.github.usalko.sy.domain;

import lombok.Data;
import lombok.Setter;
import org.jdbi.v3.core.mapper.Nested;

@Data
public class Mood {

    String id;

    @Setter(onMethod = @__(@Nested("shape")))
    GeometryShape shape;

    //List<GeometryShape> content;

}
