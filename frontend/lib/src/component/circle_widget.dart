import 'dart:math';

import 'package:flutter/cupertino.dart';

class CirclePainter extends CustomPainter {

  Size cardSize;

  CirclePainter({required this.cardSize});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = Color(0xff63aa65)
      ..style = PaintingStyle.fill;
    //a circle
    var centerX = this.cardSize.width / 2;
    var centerY = this.cardSize.height / 2;
    canvas.drawCircle(Offset(centerX, centerY), min(centerX, centerY), paint1);

  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class CircleWidget extends StatelessWidget {

  double width;

  CircleWidget({required this.width});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: CirclePainter(cardSize: Size.square(this.width)),
      ),
    );
  }
}
