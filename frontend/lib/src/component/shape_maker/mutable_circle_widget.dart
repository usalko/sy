// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

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
      this.shape = GeometryShape.Circle;
      this._indexMark = indexMark;
    }
    var content = this.widget.content!;
    for (var i = content.length - 1; i < contentIndex - 1; i++) {
      content.add(null);
    }
    var geometry = this.shape != GeometryShape.Empty
        ? Geometry(this.shape, this.widget.colorPicker.color().value)
        : null;
    if (contentIndex >= content.length) {
      content.add(geometry);
    } else {
      content[contentIndex] = geometry;
    }

    // Select next shape
    this._indexMark!.incrementCount();
    if (this._indexMark!.count % 4 == 0) {
      this.shape = GeometryShape.Circle;
    } else if (this._indexMark!.count % 4 == 1) {
      this.shape = GeometryShape.Triangle;
    } else if (this._indexMark!.count % 4 == 2) {
      this.shape = GeometryShape.Square;
    } else if (this._indexMark!.count % 4 == 3) {
      this.shape = GeometryShape.Empty;
    }
  }
}
