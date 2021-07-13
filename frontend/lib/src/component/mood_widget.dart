import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/geometry_shape.dart';
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

class _MoodWidgetState extends State<MoodWidget> {
  Color screenPickerColor = Colors.white;

  @override
  Widget build(BuildContext context) {
    widget.moodService.apiDisableEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {});
      }
    });

    var cards = <Widget>[];
    var size = MediaQuery.of(context).size;

    if (widget.viewModeService.Screen == TheScreen.Screen1) {
      var cardWidth = size.width / 3 - 10;
      cards = [
        GestureDetector(
          onTap: () => setState(() {
            this.widget.viewModeService.Screen = TheScreen.Screen2Triangle;
          }),
          child: Card(
            child: TriangleWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
            ),
          ),
        ),
        GestureDetector(
          onTap: () => setState(() {
            this.widget.viewModeService.Screen = TheScreen.Screen2Square;
          }),
          child: Card(
            child: SquareWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
            ),
          ),
        ),
        GestureDetector(
          onTap: () => setState(() {
            this.widget.viewModeService.Screen = TheScreen.Screen2Circle;
          }),
          child: Card(
            child: CircleWidget(
              width: cardWidth,
              color: Theme.of(context).dividerColor,
            ),
          ),
        ),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Triangle) {
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Triangle),
        Card(
          child: TriangleWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            editMode: true,
          ),
        ),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Square) {
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Square),
        Card(
          child: SquareWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            editMode: true,
          ),
        ),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Circle) {
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Circle),
        Card(
          child: CircleWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            editMode: true,
          ),
        ),
      ];
    } else {
      throw new UnimplementedError();
    }

    return Row(
      mainAxisAlignment: MainAxisAlignment.center,
      children: cards,
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
                setState(() => screenPickerColor = color),
            width: 44,
            height: 44,
            borderRadius: borderRadius,
            subheading: Divider(),
          ),
        ),
      ),
    );
  }
}
