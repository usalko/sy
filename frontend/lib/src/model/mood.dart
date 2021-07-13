import 'package:frontend/src/model/geometry_shape.dart';

import 'geometry.dart';

/// Basic class
class Mood {
  String id;

  GeometryShape kind;
  List<Geometry?> content;

  Mood(this.id, this.kind, this.content);

  static triangle(List<Geometry?> content) {
    return Mood('', GeometryShape.Triangle, content);
  }

  static square(List<Geometry?> content) {
    return Mood('', GeometryShape.Square, content);
  }

  static circle(List<Geometry?> content) {
    return Mood('', GeometryShape.Circle, content);
  }
}
