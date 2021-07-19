import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/shape_maker/circle_painter.dart';
import 'package:frontend/src/model/geometry.dart';

class CircleWidget extends StatelessWidget {
  final double width;
  final Color color;
  final bool showGrid;
  final bool showBorder;
  final List<Geometry?>? content;

  CircleWidget(
      {required this.width,
      this.color = Colors.white,
      this.content,
      this.showBorder = true,
      this.showGrid = false});

  @override
  Widget build(BuildContext context) {
    return Container(
      height: this.width,
      width: this.width,
      child: CustomPaint(
        painter: CirclePainter(
            color: this.color,
            content: this.content,
            showBorder: this.showBorder,
            showGrid: this.showGrid),
      ),
    );
  }
}
