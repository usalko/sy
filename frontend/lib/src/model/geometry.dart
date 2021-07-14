import 'dart:ui';

import 'package:frontend/src/model/geometry_shape.dart';

/// Basic class
class Geometry {
  GeometryShape shape;
  int color;

  Geometry(this.shape, this.color);

  factory Geometry.fromJson(Map<String, dynamic> json) {
    return Geometry(
      GeometryShapeExt.fromRaw(json['shape']),
      json['color'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'shape': GeometryShapeExt.toJson(shape),
      'color': color,
    };
  }

  static Geometry triangle(int color) {
    return new Geometry(GeometryShape.Triangle, color);
  }
  static Geometry square(int color) {
    return new Geometry(GeometryShape.Square, color);
  }
  static Geometry circle(int color) {
    return new Geometry(GeometryShape.Circle, color);
  }

  String toString() => "Geometry:${GeometryShapeExt.toJson(shape)},${Color(color)}";
}
