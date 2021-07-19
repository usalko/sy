import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/i_color_picker.dart';
import 'package:frontend/src/component/shape_maker/circle_painter.dart';
import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:frontend/src/model/index_mark.dart';

class MutableCircleWidget extends StatefulWidget {
  final double width;
  final Color color;
  final bool showGrid;
  final bool showBorder;
  final IColorPicker colorPicker;
  final List<Geometry?>? content;

  MutableCircleWidget(
      {required this.width,
      this.color = Colors.white,
      required this.colorPicker,
      this.content,
      this.showBorder = true,
      this.showGrid = false});

  @override
  _MutableCircleWidgetState createState() => _MutableCircleWidgetState();
}

class _MutableCircleWidgetState extends State<MutableCircleWidget> {
  GeometryShape shape = GeometryShape.Circle;
  IndexMark? _indexMark;

  @override
  Widget build(BuildContext context) {
    var circlePainter = CirclePainter(
        color: this.widget.color,
        content: this.widget.content,
        showBorder: this.widget.showBorder,
        showGrid: this.widget.showGrid);
    return GestureDetector(
        onTapDown: (details) {
          var contentIndex = circlePainter.index(details.localPosition);
          if (contentIndex != null && widget.content != null) {
            _updateContent(contentIndex);
            this.setState(() {});
          }
        },
        child: Container(
            height: this.widget.width,
            width: this.widget.width,
            child: CustomPaint(
              painter: circlePainter,
            )));
  }

  void _updateContent(int contentIndex) {
    var indexMark = IndexMark.of(contentIndex);
    if (_indexMark != indexMark) {
      this.shape = GeometryShape.Square;
      this._indexMark = indexMark;
    }
    var content = this.widget.content!;
    for (var i = content.length - 1; i < contentIndex - 1; i++) {
      content.add(null);
    }
    var geometry = Geometry(this.shape, this.widget.colorPicker.color().value);
    if (contentIndex >= content.length) {
      content.add(geometry);
    } else {
      content[contentIndex] = geometry;
    }

    // Select next shape
    this._indexMark!.incrementCount();
    if (this._indexMark!.count % 3 == 0) {
      this.shape = GeometryShape.Circle;
    } else if (this._indexMark!.count % 3 == 1) {
      this.shape = GeometryShape.Triangle;
    } else if (this._indexMark!.count % 3 == 2) {
      this.shape = GeometryShape.Square;
    }
  }
}
