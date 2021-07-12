import 'dart:math';

import 'package:flutter/cupertino.dart';

class TrianglePainter extends CustomPainter {
  Size cardSize;
  Color color;
  bool editMode;

  TrianglePainter({required this.cardSize, required this.color, required this.editMode});

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

    // Draw zones
    if (editMode) {
      var line1Offset = size.width / 3;
      canvas.drawLine(Offset(line1Offset, size.height / 3), Offset(size.width - line1Offset, size.height / 3), paint1);
      var line2Offset = size.width / 6;
      canvas.drawLine(Offset(line2Offset, size.height * 2 / 3), Offset(size.width - line2Offset, size.height * 2 / 3), paint1);
      canvas.drawLine(Offset(line1Offset, size.height / 3), Offset(size.width * 2 / 3, size.height), paint1);
      canvas.drawLine(Offset(size.width - line1Offset, size.height / 3), Offset(size.width / 3, size.height), paint1);
      canvas.drawLine(Offset(line2Offset, size.height * 2 / 3), Offset(size.width / 3, size.height), paint1);
      canvas.drawLine(Offset(size.width - line2Offset, size.height * 2 / 3), Offset(size.width * 2 / 3, size.height), paint1);
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
        painter: TrianglePainter(cardSize: Size.square(this.width), color: this.color, editMode: this.editMode),
      ),
    );
  }
}
