// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

import 'package:frontend/src/model/geometry_shape.dart';

import 'geometry.dart';

/// Basic class
class Mood {
  String id;
  DateTime? created;

  GeometryShape kind;
  List<Geometry?> content;

  Mood(this.id, this.kind, this.content, {this.created});

  factory Mood.fromJson(Map<String, dynamic> json) {
    return Mood(
      json['id'],
      GeometryShapeExt.fromRaw(json['kind'] as String),
      (json['content'] as List<dynamic>)
          .map((e) => e == null ? null : Geometry.fromJson(e))
          .toList(),
      created: json['created'] != null ? DateTime.parse(json['created']) : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'created': created?.toIso8601String(),
      'kind': GeometryShapeExt.toJson(kind),
      'content': content.map((e) => e != null ? e.toJson() : null).toList()
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
