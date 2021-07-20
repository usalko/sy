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

import 'dart:math';

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/i_color_picker.dart';
import 'package:frontend/src/component/mood_actions_widget.dart';
import 'package:frontend/src/component/mood_card_widget.dart';
import 'package:frontend/src/component/shape_maker/mutable_circle_widget.dart';
import 'package:frontend/src/component/shape_maker/mutable_square_widget.dart';
import 'package:frontend/src/component/shape_maker/mutable_triangle_widget.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';
import 'package:frontend/src/component/circle_widget.dart';
import 'package:flex_color_picker/flex_color_picker.dart';

class MoodWidget extends StatefulWidget {
  MoodWidget(
      {Key? key, required this.moodService, required this.viewModeService})
      : super(key: key);

  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _MoodWidgetState createState() => _MoodWidgetState();
}

class _MoodWidgetState extends State<MoodWidget> implements IColorPicker {
  Color screenPickerColor = Colors.red;
  Mood? mood;

  @override
  void initState() {
    super.initState();
    widget.viewModeService.screenChangeEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {});
      }
    });
  }

  @override
  void dispose() {
    widget.viewModeService.screenChangeEvent.unsubscribeAll();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    var cards = <Widget>[];
    final size = MediaQuery.of(context).size;

    final actionsWidget = MoodActionsWidget(
      onShare: () async {
        if (await this
            .widget
            .moodService
            .shareMood(this.widget.moodService.token, this.mood!)) {
          this.setState(() {
            this.widget.viewModeService.screen = TheScreen.Screen1;
          });
        }
      },
      onClose: () => this.setState(() {
        this.widget.viewModeService.screen = TheScreen.Screen1;
      }),
      onKeepForNow: () async {
        if (await this
            .widget
            .moodService
            .keepMoodForNow(this.widget.moodService.token, this.mood!)) {
          this.setState(() {
            this.widget.viewModeService.screen = TheScreen.Screen1;
          });
        }
      },
    );

    if (widget.viewModeService.screen == TheScreen.Screen1) {
      var cardWidth = max(size.width, 740.0) / 3 - 30;
      cards = [
        GestureDetector(
          onTap: () => setState(() {
            this.widget.viewModeService.screen = TheScreen.Screen2Triangle;
          }),
          child: MoodCardWidget(
            child: TriangleWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
            ),
          ),
        ),
        GestureDetector(
          onTap: () => setState(() {
            this.widget.viewModeService.screen = TheScreen.Screen2Square;
          }),
          child: MoodCardWidget(
            child: SquareWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
            ),
          ),
        ),
        GestureDetector(
          onTap: () => setState(() {
            this.widget.viewModeService.screen = TheScreen.Screen2Circle;
          }),
          child: MoodCardWidget(
            child: CircleWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
            ),
          ),
        ),
      ];
    } else if (widget.viewModeService.screen == TheScreen.Screen2Triangle) {
      this.mood = Mood.triangle(this.mood != null ? this.mood!.content : []);
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Triangle),
        Padding(
          padding: EdgeInsets.only(top: 66),
          child: MoodCardWidget(
            child: MutableTriangleWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
              showGrid: true,
              colorPicker: this,
              content: mood!.content,
            ),
          ),
        ),
        actionsWidget,
      ];
    } else if (widget.viewModeService.screen == TheScreen.Screen2Square) {
      this.mood = Mood.square(this.mood != null ? this.mood!.content : []);
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Square),
        Padding(
          padding: EdgeInsets.only(top: 66),
          child: MoodCardWidget(
            child: MutableSquareWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
              showGrid: true,
              colorPicker: this,
              content: mood!.content,
            ),
          ),
        ),
        actionsWidget,
      ];
    } else if (widget.viewModeService.screen == TheScreen.Screen2Circle) {
      this.mood = Mood.circle(this.mood != null ? this.mood!.content : []);
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Circle),
        Padding(
          padding: EdgeInsets.only(top: 66),
          child: MoodCardWidget(
            child: MutableCircleWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
              showGrid: true,
              colorPicker: this,
              content: mood!.content,
            ),
          ),
        ),
        actionsWidget,
      ];
    } else {
      throw new UnimplementedError();
    }

    return Padding(
      padding: EdgeInsets.only(top: 6),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.center,
        children: cards,
      ),
    );
  }

  Widget colorPicker(BuildContext context, GeometryShape geometryShape) {
    var borderRadius = 0.0;
    if (geometryShape == GeometryShape.Circle) {
      borderRadius = 22;
    } else if (geometryShape == GeometryShape.Square) {
      borderRadius = 0;
    } else if (geometryShape == GeometryShape.Triangle) {
      borderRadius = 0;
    }

    return SizedBox(
      width: 200,
      child: Padding(
        padding: const EdgeInsets.all(6),
        child: Card(
          color: Theme.of(context).canvasColor,
          elevation: 0,
          child: ColorPicker(
            pickerTypeLabels: Map.of(
                {ColorPickerType.primary: '|', ColorPickerType.accent: '||'}),
            // Use the screenPickerColor as start color.
            color: screenPickerColor,
            // Update the screenPickerColor using the callback.
            onColorChanged: (Color color) =>
                setState(() => this.screenPickerColor = color),
            width: 44,
            height: 44,
            borderRadius: borderRadius,
            subheading: Divider(),
          ),
        ),
      ),
    );
  }

  @override
  Color color() {
    return this.screenPickerColor;
  }
}
