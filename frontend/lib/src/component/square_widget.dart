import 'dart:math';

import 'package:flutter/cupertino.dart';

class SquarePainter extends CustomPainter {

  Size cardSize;
  Color color;

  SquarePainter({required this.cardSize, required this.color});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;
    // a square
    var centerX = this.cardSize.width / 2;
    var centerY = this.cardSize.height / 2;
    canvas.drawRect(Rect.fromCenter(center: Offset(centerX, centerY), width: cardSize.width, height: cardSize.height), paint1);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class SquareWidget extends StatelessWidget {

  double width;
  Color color;

  SquareWidget({required this.width, required this.color});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: SquarePainter(cardSize: Size.square(this.width), color: this.color),
      ),
    );
  }
}
