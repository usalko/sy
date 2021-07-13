import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CirclePainter extends CustomPainter {

  Color color;
  bool editMode;

  CirclePainter({required this.color, required this.editMode});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;
    // a circle
    var centerX = size.width / 2;
    var centerY = size.height / 2;
    canvas.drawCircle(Offset(centerX, centerY), min(centerX, centerY), paint1);

    // Draw zones
    if (editMode) {
      var squareSide = min(size.width, size.height);
      var halfSquareSide = squareSide / 2;
      var radius = squareSide / 6;
      var topLeft = Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);
      canvas.drawCircle(Offset(halfSquareSide, halfSquareSide) + topLeft, radius, paint1);

      canvas.drawCircle(Offset(halfSquareSide, halfSquareSide / 3) + topLeft, radius, paint1);
      canvas.drawCircle(Offset(halfSquareSide, halfSquareSide * (1 + 2 / 3)) + topLeft, radius, paint1);
      canvas.drawCircle(Offset(halfSquareSide / 3, halfSquareSide) + topLeft, radius, paint1);
      canvas.drawCircle(Offset(halfSquareSide * (1 + 2 / 3), halfSquareSide) + topLeft, radius, paint1);

      canvas.drawCircle(Offset(halfSquareSide / 1.9, halfSquareSide / 1.9) + topLeft, radius, paint1);
      canvas.drawCircle(Offset(halfSquareSide * (2 - 1 / 1.9), halfSquareSide / 1.9) + topLeft, radius, paint1);
      canvas.drawCircle(Offset(halfSquareSide / 1.9, halfSquareSide * (2 - 1 / 1.9)) + topLeft, radius, paint1);
      canvas.drawCircle(Offset(halfSquareSide * (2 - 1 / 1.9), halfSquareSide * (2 - 1 / 1.9)) + topLeft, radius, paint1);
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class CircleWidget extends StatelessWidget {

  double width;
  Color color;
  bool editMode;

  CircleWidget({required this.width, required this.color, this.editMode: false});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: CirclePainter(color: color, editMode: editMode),
      ),
    );
  }
}
