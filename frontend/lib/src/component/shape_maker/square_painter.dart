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

import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:collection/collection.dart';

class SquarePainter extends CustomPainter {
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

  // Index stuff
  Size? _size;
  List<List<Offset>>? _index;

  SquarePainter(
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

    var centerX = size.width / 2;
    var centerY = size.height / 2;
    var squareSide = min(size.width, size.height);

    // Draw border
    if (this.showBorder) {
      canvas.drawRect(
          Rect.fromCenter(
              center: Offset(centerX, centerY),
              width: squareSide,
              height: squareSide),
          paint1);
    }

    // Draw zones
    if (showGrid) {
      var topLeft =
          Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);

      canvas.drawLine(Offset(squareSide / 3, 0) + topLeft,
          Offset(squareSide / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(squareSide * 2 / 3, 0) + topLeft,
          Offset(squareSide * 2 / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(0, squareSide / 3) + topLeft,
          Offset(squareSide, squareSide / 3) + topLeft, paint1);
      canvas.drawLine(Offset(0, squareSide * 2 / 3) + topLeft,
          Offset(squareSide, squareSide * 2 / 3) + topLeft, paint1);
    }

    // Revalidate index
    if (this._size != size) {
      _invalidateIndex(size);
    }
    // Draw content
    if (content != null) {
      var nineCenters = getNineCenters(size);

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
            path.lineTo(center.dx + squareSide / 6, center.dy + squareSide / 6);
            path.lineTo(center.dx - squareSide / 6, center.dy + squareSide / 6);
            path.close();
            canvas.drawPath(path, paint);
          } else if (geometry.shape == GeometryShape.Square) {
            canvas.drawRect(
                Rect.fromCenter(
                    center: center,
                    width: squareSide / 3,
                    height: squareSide / 3),
                paint);
          } else if (geometry.shape == GeometryShape.Circle) {
            canvas.drawCircle(center, squareSide / 6, paint);
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
      this._index![0].reduce((a, b) => a + b) / 4,
      this._index![1].reduce((a, b) => a + b) / 4,
      this._index![2].reduce((a, b) => a + b) / 4,
      this._index![3].reduce((a, b) => a + b) / 4,
      this._index![4].reduce((a, b) => a + b) / 4,
      this._index![5].reduce((a, b) => a + b) / 4,
      this._index![6].reduce((a, b) => a + b) / 4,
      this._index![7].reduce((a, b) => a + b) / 4,
      this._index![8].reduce((a, b) => a + b) / 4,
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
    var topLeft =
        Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);
    var p0 = topLeft;
    var p1 = Offset(squareSide / 3, 0) + topLeft;
    var p2 = Offset(squareSide * 2 / 3, 0) + topLeft;
    var p3 = Offset(squareSide, 0) + topLeft;

    var p4 = Offset(0, squareSide / 3) + topLeft;
    var p5 = Offset(squareSide / 3, squareSide / 3) + topLeft;
    var p6 = Offset(squareSide * 2 / 3, squareSide / 3) + topLeft;
    var p7 = Offset(squareSide, squareSide / 3) + topLeft;

    var p8 = Offset(0, squareSide * 2 / 3) + topLeft;
    var p9 = Offset(squareSide / 3, squareSide * 2 / 3) + topLeft;
    var p10 = Offset(squareSide * 2 / 3, squareSide * 2 / 3) + topLeft;
    var p11 = Offset(squareSide, squareSide * 2 / 3) + topLeft;

    var p12 = Offset(0, squareSide) + topLeft;
    var p13 = Offset(squareSide / 3, squareSide) + topLeft;
    var p14 = Offset(squareSide * 2 / 3, squareSide) + topLeft;
    var p15 = Offset(squareSide, squareSide) + topLeft;

    this._index = [
      [p0, p1, p4, p5],
      [p1, p2, p5, p6],
      [p2, p3, p6, p7],
      [p4, p5, p8, p9],
      [p5, p6, p9, p10],
      [p6, p7, p10, p11],
      [p8, p9, p12, p13],
      [p9, p10, p13, p14],
      [p10, p11, p14, p15],
    ];

    this._size = size;
  }

  bool _inShape(Offset point, List<Offset> value) {
    var p0 = value[0];
    var p3 = value[3];
    return p0.dx <= point.dx &&
        p3.dx >= point.dx &&
        p0.dy <= point.dy &&
        p3.dy >= point.dy;
  }
}
