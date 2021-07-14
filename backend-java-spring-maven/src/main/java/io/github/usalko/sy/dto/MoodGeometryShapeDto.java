package io.github.usalko.sy.dto;

import io.github.usalko.sy.model.GeometryShape;

public class MoodGeometryShapeDto {

    private GeometryShape geometryShape;
    private Integer color;

    public GeometryShape getGeometryShape() {
        return geometryShape;
    }

    public void setGeometryShape(GeometryShape geometryShape) {
        this.geometryShape = geometryShape;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }
}
