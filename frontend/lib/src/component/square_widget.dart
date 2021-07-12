import 'dart:math';

import 'package:flutter/cupertino.dart';

class SquarePainter extends CustomPainter {

  Size cardSize;

  SquarePainter({required this.cardSize});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = Color(0xff63aa65)
      ..style = PaintingStyle.fill;
    //a circle
    var centerX = this.cardSize.width / 2;
    var centerY = this.cardSize.height / 2;
    canvas.drawRect(Rect.fromCenter(center: Offset(centerX, centerY), width: cardSize.width, height: cardSize.height), paint1);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class SquareWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var size = MediaQuery.of(context).size;
    var width =  size.width / 3 - 10;
    return Container(
      height: width,
      width: width,
      child: CustomPaint(
        painter: SquarePainter(cardSize: Size.square(width)),
      ),
    );
  }
}
