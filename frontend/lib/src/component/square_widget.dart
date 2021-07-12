import 'dart:math';

import 'package:flutter/cupertino.dart';

class SquarePainter extends CustomPainter {

  Size cardSize;
  Color color;
  bool editMode;

  SquarePainter({required this.cardSize, required this.color, required this.editMode});

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
    // Draw zones
    if (editMode) {
      canvas.drawLine(Offset(size.width / 3, 0), Offset(size.width / 3, size.height), paint1);
      canvas.drawLine(Offset(size.width * 2 / 3, 0), Offset(size.width * 2 / 3, size.height), paint1);
      canvas.drawLine(Offset(0, size.height / 3), Offset(size.width, size.height / 3), paint1);
      canvas.drawLine(Offset(0, size.height * 2 / 3), Offset(size.width, size.height * 2 / 3), paint1);
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
        painter: SquarePainter(cardSize: Size.square(this.width), color: this.color, editMode: this.editMode),
      ),
    );
  }
}
