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

class TrianglePainter extends CustomPainter {
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

  // Index stuff
  Size? _size;
  List<List<Offset>>? _index;

  TrianglePainter(
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
    // a triangle

    var squareSide = min(size.width, size.height);
    var topLeft =
        Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);

    // Draw border
    if (showBorder) {
      var path = Path();
      path.moveTo(squareSide / 2 + topLeft.dx, topLeft.dy);
      path.lineTo(topLeft.dx, squareSide + topLeft.dy);
      path.lineTo(squareSide + topLeft.dx, squareSide + topLeft.dy);
      path.close();
      canvas.drawPath(path, paint1);
    }

    // Draw grid
    if (showGrid) {
      var line1Offset = squareSide / 3;
      canvas.drawLine(Offset(line1Offset, squareSide / 3) + topLeft,
          Offset(squareSide - line1Offset, squareSide / 3) + topLeft, paint1);
      var line2Offset = squareSide / 6;
      canvas.drawLine(
          Offset(line2Offset, squareSide * 2 / 3) + topLeft,
          Offset(squareSide - line2Offset, squareSide * 2 / 3) + topLeft,
          paint1);
      canvas.drawLine(Offset(line1Offset, squareSide / 3) + topLeft,
          Offset(squareSide * 2 / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(
          Offset(squareSide - line1Offset, squareSide / 3) + topLeft,
          Offset(squareSide / 3, squareSide) + topLeft,
          paint1);
      canvas.drawLine(Offset(line2Offset, squareSide * 2 / 3) + topLeft,
          Offset(squareSide / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(
          Offset(squareSide - line2Offset, squareSide * 2 / 3) + topLeft,
          Offset(squareSide * 2 / 3, squareSide) + topLeft,
          paint1);
    }

    // Revalidate index
    if (this._size != size) {
      _invalidateIndex(size);
    }
    // Draw content
    if (content != null) {
      var radius = tan(30 * pi / 180) *
          squareSide /
          6; // TODO: fix calculation for inscribed circle
      var triangleBase = squareSide / 3;
      var smallSquareSide =
          triangleBase * triangleBase / (triangleBase + triangleBase);

      var i = 0;
      content?.take(9).forEach((geometry) {
        if (geometry != null) {
          var center = this._index![i].reduce((a, b) => a + b) / 3;
          var paint = Paint()
            ..color = Color(geometry.color)
            ..isAntiAlias = true
            ..style = PaintingStyle.fill;
          if (geometry.shape == GeometryShape.Triangle) {
            var path = Path();
            var p0 = this._index![i][0];
            var p1 = this._index![i][1];
            var p2 = this._index![i][2];
            path.moveTo(p0.dx, p0.dy);
            path.lineTo(p1.dx, p1.dy);
            path.lineTo(p2.dx, p2.dy);
            path.close();
            canvas.drawPath(path, paint);
          } else if (geometry.shape == GeometryShape.Square) {
            var p0 = this._index![i][0];
            canvas.drawRect(
                Rect.fromCenter(
                    center: Offset(
                        p0.dx,
                        p0.dy +
                            (p0.dy < center.dy ? 3 : -3) * smallSquareSide / 2),
                    width: smallSquareSide,
                    height: smallSquareSide),
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
    var line1Offset = squareSide / 3;
    var line2Offset = squareSide / 6;

    var p0 = Offset(squareSide / 2 + topLeft.dx, topLeft.dy);
    var p1 = Offset(line1Offset, squareSide / 3) + topLeft;
    var p2 = Offset(squareSide - line1Offset, squareSide / 3) + topLeft;
    var p3 = Offset(line2Offset, squareSide * 2 / 3) + topLeft;
    var p4 = Offset(squareSide / 2, squareSide * 2 / 3) + topLeft;
    var p5 = Offset(squareSide - line2Offset, squareSide * 2 / 3) + topLeft;
    var p6 = Offset(topLeft.dx, squareSide + topLeft.dy);
    var p7 = Offset(squareSide / 3, squareSide) + topLeft;
    var p8 = Offset(squareSide * 2 / 3, squareSide) + topLeft;
    var p9 = Offset(topLeft.dx + squareSide, squareSide + topLeft.dy);

    this._index = [
      [p0, p2, p1],
      [p1, p4, p3],
      [p4, p1, p2],
      [p2, p5, p4],
      [p3, p7, p6],
      [p7, p3, p4],
      [p4, p8, p7],
      [p8, p4, p5],
      [p5, p9, p8],
    ];

    this._size = size;
  }

  bool _inShape(Offset point, List<Offset> value) {
    var p0 = value[0];
    var p1 = value[1];
    var p2 = value[2];
    var d1 = sign(point, p0, p1);
    var d2 = sign(point, p1, p2);
    var d3 = sign(point, p2, p0);

    var hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
    var hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

    return !(hasNeg && hasPos);
  }

  double sign(Offset p1, Offset p2, Offset p3) {
    return (p1.dx - p3.dx) * (p2.dy - p3.dy) -
        (p2.dx - p3.dx) * (p1.dy - p3.dy);
  }
}
