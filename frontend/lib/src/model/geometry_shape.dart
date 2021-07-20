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

enum GeometryShape { Empty, Triangle, Square, Circle }

extension GeometryShapeExt on GeometryShape {
  static const Map<GeometryShape, String?> keys = {
    GeometryShape.Empty: null,
    GeometryShape.Triangle: 'triangle',
    GeometryShape.Square: 'square',
    GeometryShape.Circle: 'circle',
  };
  static const Map<String?, GeometryShape> values = {
    null: GeometryShape.Empty,
    'triangle': GeometryShape.Triangle,
    'square': GeometryShape.Square,
    'circle': GeometryShape.Circle,
  };

  String? get key => keys[this];

  static GeometryShape fromRaw(String raw) {
    var value = values[raw.toLowerCase()];
    if (value != null) {
      return value;
    }
    throw new Exception('Unknow key $raw for enumaration GeometryShape');
  }

  static String? toJson(GeometryShape geometryShape) {
    return keys[geometryShape];
  }
}
