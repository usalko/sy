package io.github.usalko.sy.domain;

import lombok.Data;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

@Data
public class MoodGeometryShape {

    @ColumnName("index_in_list")
    int indexInList;

    int color;

    @ColumnName("own_mood_id")
    long ownMoodId;

    GeometryShape shape;

    @ColumnName("geometry_shape_id")
    long geometryShapeId;

}
