import 'dart:math';

import 'package:flutter/cupertino.dart';

class TrianglePainter extends CustomPainter {
  Color color;
  bool editMode;

  TrianglePainter({required this.color, required this.editMode});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;
    // a triangle

    var squareSide = min(size.width, size.height);
    var topLeft = Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);

    var path = Path();
    path.moveTo(squareSide / 2 + topLeft.dx, topLeft.dy);
    path.lineTo(topLeft.dx, squareSide + topLeft.dy);
    path.lineTo(squareSide + topLeft.dx, squareSide + topLeft.dy);
    path.close();
    canvas.drawPath(path, paint1);

    // Draw zones
    if (editMode) {
      var line1Offset = squareSide / 3;
      canvas.drawLine(Offset(line1Offset, squareSide / 3) + topLeft, Offset(squareSide - line1Offset, squareSide / 3) + topLeft, paint1);
      var line2Offset = squareSide / 6;
      canvas.drawLine(Offset(line2Offset, squareSide * 2 / 3) + topLeft, Offset(squareSide - line2Offset, squareSide * 2 / 3) + topLeft, paint1);
      canvas.drawLine(Offset(line1Offset, squareSide / 3) + topLeft, Offset(squareSide * 2 / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(squareSide - line1Offset, squareSide / 3) + topLeft, Offset(squareSide / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(line2Offset, squareSide * 2 / 3) + topLeft, Offset(squareSide / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(squareSide - line2Offset, squareSide * 2 / 3) + topLeft, Offset(squareSide * 2 / 3, squareSide) + topLeft, paint1);
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class TriangleWidget extends StatelessWidget {
  double width;
  Color color;
  bool editMode;

  TriangleWidget({required this.width, required this.color, this.editMode = false});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: TrianglePainter(color: this.color, editMode: this.editMode),
      ),
    );
  }
}
