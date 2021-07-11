import 'package:frontend/src/model/geometry_shape.dart';

import 'geometry.dart';

/// Basic class
class Mood {
  String id;

  GeometryShape kind;
  List<Geometry> content;

  Mood(this.id, this.kind, this.content);
}
