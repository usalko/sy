import 'package:frontend/src/model/geometry_shape.dart';

/// Basic class
class Geometry {
  GeometryShape shape;
  int color;

  Geometry(this.shape, this.color);

  static Geometry triangle(int color) {
    return new Geometry(GeometryShape.Triangle, color);
  }
  static Geometry square(int color) {
    return new Geometry(GeometryShape.Square, color);
  }
  static Geometry circle(int color) {
    return new Geometry(GeometryShape.Circle, color);
  }
}
