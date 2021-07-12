import 'dart:math';

import 'package:flutter/cupertino.dart';

class TrianglePainter extends CustomPainter {
  Size cardSize;
  Color color;

  TrianglePainter({required this.cardSize, required this.color});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;
    // a triangle
    var path = Path();
    path.moveTo(size.width / 2, 0);
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
  Color color;

  TriangleWidget({required this.width, required this.color});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: TrianglePainter(cardSize: Size.square(this.width), color: this.color),
      ),
    );
  }
}
