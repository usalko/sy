import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/geometry_shape.dart';

class SquarePainter extends CustomPainter {
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

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
    var squareSide = min(size.width, size.height);
    var topLeft =
        Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);
    var p0 = topLeft;
    var p1 = Offset(squareSide / 3, 0) + topLeft;
    var p2 = Offset(squareSide * 2 / 3, 0) + topLeft;
    var p3 = Offset(squareSide, 0) + topLeft;

    var p5 = Offset(0, squareSide / 3) + topLeft;
    var p6 = Offset(squareSide / 3, squareSide / 3) + topLeft;
    var p7 = Offset(squareSide * 2 / 3, squareSide / 3) + topLeft;
    var p8 = Offset(squareSide, squareSide / 3) + topLeft;

    var p9 = Offset(0, squareSide * 2 / 3) + topLeft;
    var p10 = Offset(squareSide / 3, squareSide * 2 / 3) + topLeft;
    var p11 = Offset(squareSide * 2 / 3, squareSide * 2 / 3) + topLeft;
    var p12 = Offset(squareSide, squareSide * 2 / 3) + topLeft;

    var p13 = Offset(0, squareSide) + topLeft;
    var p14 = Offset(squareSide / 3, squareSide) + topLeft;
    var p15 = Offset(squareSide * 2 / 3, squareSide) + topLeft;
    var p16 = Offset(squareSide, squareSide) + topLeft;

    return [
      (p0 + p1 + p5 + p6) / 4,
      (p1 + p2 + p6 + p7) / 4,
      (p2 + p3 + p7 + p8) / 4,
      (p5 + p6 + p9 + p10) / 4,
      (p6 + p7 + p10 + p11) / 4,
      (p7 + p8 + p11 + p12) / 4,
      (p9 + p10 + p13 + p14) / 4,
      (p10 + p11 + p14 + p15) / 4,
      (p11 + p12 + p15 + p16) / 4,
    ];
  }
}

class SquareWidget extends StatelessWidget {
  double width;
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

  SquareWidget(
      {required this.width,
      this.color = Colors.white,
      this.content = null,
      this.showBorder = true,
      this.showGrid = false});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: SquarePainter(
            color: this.color,
            content: this.content,
            showBorder: this.showBorder,
            showGrid: this.showGrid),
      ),
    );
  }
}
