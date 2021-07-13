import 'dart:math';

import 'package:flutter/cupertino.dart';

class SquarePainter extends CustomPainter {

  Color color;
  bool editMode;

  SquarePainter({required this.color, required this.editMode});

  @override
  void paint(Canvas canvas, Size size) {
    var paint1 = Paint()
      ..color = this.color
      ..isAntiAlias = true
      ..strokeWidth = 2.0
      ..style = PaintingStyle.stroke;
    // a square
    var centerX = size.width / 2;
    var centerY = size.height / 2;
    var squareSide = min(size.width, size.height);
    canvas.drawRect(Rect.fromCenter(center: Offset(centerX, centerY), width: squareSide, height: squareSide), paint1);
    // Draw zones
    if (editMode) {
      var topLeft = Offset((size.width - squareSide) / 2, (size.height - squareSide) / 2);

      canvas.drawLine(Offset(squareSide / 3, 0) + topLeft, Offset(squareSide / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(squareSide * 2 / 3, 0) + topLeft, Offset(squareSide * 2 / 3, squareSide) + topLeft, paint1);
      canvas.drawLine(Offset(0, squareSide / 3) + topLeft, Offset(squareSide, squareSide / 3) + topLeft, paint1);
      canvas.drawLine(Offset(0, squareSide * 2 / 3) + topLeft, Offset(squareSide, squareSide * 2 / 3) + topLeft, paint1);
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) => true;
}

class SquareWidget extends StatelessWidget {

  double width;
  Color color;
  bool editMode;

  SquareWidget({required this.width, required this.color, this.editMode = false});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: SquarePainter(color: this.color, editMode: this.editMode),
      ),
    );
  }
}
