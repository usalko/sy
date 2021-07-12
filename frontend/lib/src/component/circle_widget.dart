import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class CirclePainter extends CustomPainter {

  Size cardSize;
  Color color;

  CirclePainter({required this.cardSize, required this.color});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;
    // a circle
    var centerX = this.cardSize.width / 2;
    var centerY = this.cardSize.height / 2;
    canvas.drawCircle(Offset(centerX, centerY), min(centerX, centerY), paint1);

  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class CircleWidget extends StatelessWidget {

  double width;
  Color color;

  CircleWidget({required this.width, required this.color});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: CirclePainter(cardSize: Size.square(this.width), color: color),
      ),
    );
  }
}
