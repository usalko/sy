enum GeometryShape {
  Triangle,
  Square,
  Circle
}

extension GeometryShapeExt on GeometryShape {
  static const Map<GeometryShape, String> keys = {
    GeometryShape.Triangle: 'triangle',
    GeometryShape.Square: 'square',
    GeometryShape.Circle: 'circle',
  };
  static const Map<String, GeometryShape> values = {
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
