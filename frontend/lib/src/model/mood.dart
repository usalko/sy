import 'package:frontend/src/model/geometry_shape.dart';

import 'geometry.dart';

/// Basic class
class Mood {
  String id;
  DateTime? created;

  GeometryShape kind;
  List<Geometry?> content;

  Mood(this.id, this.kind, this.content, {this.created = null});

  factory Mood.fromJson(Map<String, dynamic> json) {
    return Mood(
      json['id'],
      GeometryShapeExt.fromRaw(json['kind'] as String),
      (json['content'] as List<dynamic>).map((e) => e == null ? null : Geometry.fromJson(e)).toList(),
      created: json['created'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'created': created,
      'kind': GeometryShapeExt.toJson(kind),
      'content': content.map((e) => e != null ? e.toJson(): null).toList()
    };
  }

  static Mood triangle(List<Geometry?> content, {DateTime? created}) {
    return Mood('', GeometryShape.Triangle, content, created: created);
  }

  static Mood square(List<Geometry?> content, {DateTime? created}) {
    return Mood('', GeometryShape.Square, content, created: created);
  }

  static Mood circle(List<Geometry?> content, {DateTime? created}) {
    return Mood('', GeometryShape.Circle, content, created: created);
  }
}
