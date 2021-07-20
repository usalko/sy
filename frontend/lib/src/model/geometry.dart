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
