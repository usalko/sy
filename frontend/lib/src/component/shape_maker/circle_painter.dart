import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:collection/collection.dart';

class CirclePainter extends CustomPainter {
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

  // Index stuff
  Size? _size;
  List<List<Offset>>? _index;

    CirclePainter(
      {required this.color,
      required this.content,
      required this.showBorder,
      required this.showGrid});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;

    var squareSide = min(size.width, size.height);
    // Draw border
    if (this.showBorder) {
      var centerX = size.width / 2;
      var centerY = size.height / 2;
      canvas.drawCircle(
          Offset(centerX, centerY), min(centerX, centerY), paint1);
    }

    // Draw zones
    if (showGrid) {
      var halfSquareSide = squareSide / 2;
      var radius = squareSide / 6;
      var topLeft =
          Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);
      canvas.drawCircle(
          Offset(halfSquareSide, halfSquareSide) + topLeft, radius, paint1);

      canvas.drawCircle(
          Offset(halfSquareSide, halfSquareSide / 3) + topLeft, radius, paint1);
      canvas.drawCircle(
          Offset(halfSquareSide, halfSquareSide * (1 + 2 / 3)) + topLeft,
          radius,
          paint1);
      canvas.drawCircle(
          Offset(halfSquareSide / 3, halfSquareSide) + topLeft, radius, paint1);
      canvas.drawCircle(
          Offset(halfSquareSide * (1 + 2 / 3), halfSquareSide) + topLeft,
          radius,
          paint1);

      canvas.drawCircle(
          Offset(halfSquareSide / 1.9, halfSquareSide / 1.9) + topLeft,
          radius,
          paint1);
      canvas.drawCircle(
          Offset(halfSquareSide * (2 - 1 / 1.9), halfSquareSide / 1.9) +
              topLeft,
          radius,
          paint1);
      canvas.drawCircle(
          Offset(halfSquareSide / 1.9, halfSquareSide * (2 - 1 / 1.9)) +
              topLeft,
          radius,
          paint1);
      canvas.drawCircle(
          Offset(halfSquareSide * (2 - 1 / 1.9),
                  halfSquareSide * (2 - 1 / 1.9)) +
              topLeft,
          radius,
          paint1);
    }

    // Revalidate index
    if (this._size != size) {
      _invalidateIndex(size);
    }
    // Draw content
    if (content != null) {
      var nineCenters = getNineCenters(size);
      var radius = squareSide / 6;
      var squareSideInscribedInCircle = sqrt(2*radius*radius);
      var triangleDX = cos(30*pi/180)*radius;
      var triangleDY = sin(30*pi/180)*radius;

      var i = 0;
      content?.take(9).forEach((geometry) {
        if (geometry != null) {
          var center = nineCenters[i];
          var paint = Paint()
            ..color = Color(geometry.color)
            ..isAntiAlias = true
            ..style = PaintingStyle.fill;
          if (geometry.shape == GeometryShape.Triangle) {
            var path = Path();
            path.moveTo(center.dx, center.dy - squareSide / 6);
            path.lineTo(center.dx + triangleDX, center.dy + triangleDY);
            path.lineTo(center.dx - triangleDX, center.dy + triangleDY);
            path.close();
            canvas.drawPath(path, paint);
          } else if (geometry.shape == GeometryShape.Square) {
            canvas.drawRect(
                Rect.fromCenter(
                    center: center,
                    width: squareSideInscribedInCircle,
                    height: squareSideInscribedInCircle),
                paint);
          } else if (geometry.shape == GeometryShape.Circle) {
            canvas.drawCircle(center, radius, paint);
          } else {
            throw new UnimplementedError();
          }
        }
        i++;
      });
    }
  }
  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;

  List<Offset> getNineCenters(Size size) {
    return [
      this._index![0][0],
      this._index![1][0],
      this._index![2][0],
      this._index![3][0],
      this._index![4][0],
      this._index![5][0],
      this._index![6][0],
      this._index![7][0],
      this._index![8][0],
    ];
  }

  int? index(Offset localPosition) {
    return this
        ._index!
        .asMap()
        .entries
        .firstWhereOrNull((e) => _inShape(localPosition, e.value))
        ?.key;
  }

  void _invalidateIndex(Size size) {
    var squareSide = min(size.width, size.height);
    var halfSquareSide = squareSide / 2;
    var topLeft =
        Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);
    var p0 = Offset(halfSquareSide, halfSquareSide) + topLeft;
    var p1 = Offset(halfSquareSide, halfSquareSide / 3) + topLeft;
    var p2 = Offset(halfSquareSide, halfSquareSide * (1 + 2 / 3)) + topLeft;
    var p3 = Offset(halfSquareSide / 3, halfSquareSide) + topLeft;
    var p4 = Offset(halfSquareSide * (1 + 2 / 3), halfSquareSide) + topLeft;
    var p5 = Offset(halfSquareSide / 1.9, halfSquareSide / 1.9) + topLeft;
    var p6 = Offset(halfSquareSide * (2 - 1 / 1.9), halfSquareSide / 1.9) + topLeft;
    var p7 = Offset(halfSquareSide / 1.9, halfSquareSide * (2 - 1 / 1.9)) + topLeft;
    var p8 = Offset(halfSquareSide * (2 - 1 / 1.9), halfSquareSide * (2 - 1 / 1.9)) + topLeft;

    this._index = [
      [p0],
      [p1],
      [p2],
      [p3],
      [p4],
      [p5],
      [p6],
      [p7],
      [p8],
    ];

    this._size = size;
  }

  bool _inShape(Offset point, List<Offset> value) {
    var squareSide = min(_size!.width, _size!.height);
    var radius = squareSide / 6;
    var p0 = value[0];
    var vector = point - p0;
    // Calculate norma
    return sqrt(vector.dx * vector.dx + vector.dy * vector.dy) <= radius;
  }
}
