import 'dart:math';

import 'package:flutter/cupertino.dart';

class TrianglePainter extends CustomPainter {

  Size cardSize;

  TrianglePainter({required this.cardSize});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = Color(0xff63aa65)
      ..style = PaintingStyle.fill;
    //a circle
    var centerX = this.cardSize.width / 2;
    var centerY = this.cardSize.height / 2;
    //canvas.drawArc(Rect.fromCenter(center: Offset(centerX, centerY), width: cardSize.width, height: cardSize.height), 0, 180, true, paint1);
    //canvas.drawCircle(Offset(centerX, centerY), min(centerX, centerY), paint1);

    var path = Path();

    path.moveTo(size.width/2, 0);
    path.lineTo(0, size.height);
    path.lineTo(size.height, size.width);
    path.close();
    canvas.drawPath(path, paint1);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class TriangleWidget extends StatelessWidget {

  double width;

  TriangleWidget({required this.width});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: TrianglePainter(cardSize: Size.square(this.width)),
      ),
    );
  }
}
