import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/model/geometry.dart';

class CirclePainter extends CustomPainter {
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

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

    // Draw border
    if (this.showBorder) {
      var centerX = size.width / 2;
      var centerY = size.height / 2;
      canvas.drawCircle(
          Offset(centerX, centerY), min(centerX, centerY), paint1);
    }

    // Draw zones
    if (showGrid) {
      var squareSide = min(size.width, size.height);
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

    // Draw content
    if (content != null) {
      content?.take(9).forEach((element) {});
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class CircleWidget extends StatelessWidget {
  double width;
  Color color;
  bool showGrid;
  bool showBorder;
  List<Geometry?>? content;

  CircleWidget(
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
        painter: CirclePainter(
            color: this.color,
            content: this.content,
            showBorder: this.showBorder,
            showGrid: this.showGrid),
      ),
    );
  }
}
