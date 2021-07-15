import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
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

class _MoodWidgetState extends State<MoodWidget> {
  Color screenPickerColor = Colors.white;
  Mood? mood;

  @override
  Widget build(BuildContext context) {
    widget.viewModeService.screenChangeEvent.subscribe((args) {
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
      this.mood = Mood.triangle(this.mood != null ? this.mood!.content : []);
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Triangle),
        Card(
          child: TriangleWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            showGrid: true,
          ),
        ),
        actionButtons(context),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Square) {
      this.mood = Mood.square(this.mood != null ? this.mood!.content : []);
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Square),
        Card(
          child: SquareWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            showGrid: true,
          ),
        ),
        actionButtons(context),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Circle) {
      this.mood = Mood.circle(this.mood != null ? this.mood!.content : []);
      var cardWidth = size.width / 1.9;
      cards = [
        this.colorPicker(context, GeometryShape.Circle),
        Card(
          child: CircleWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            showGrid: true,
          ),
        ),
        actionButtons(context),
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

  Widget actionButtons(BuildContext context) {
    return SizedBox(
      width: 130,
      child: Padding(
        padding: const EdgeInsets.all(0),
        child: Card(
            color: Theme.of(context).canvasColor,
            elevation: 0,
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      IconButton(
                        onPressed: () async {
                          if (await this.widget.moodService.ShareMood(
                              this.widget.moodService.Token, this.mood!)) {
                            setState(() {
                              this.widget.viewModeService.Screen =
                                  TheScreen.Screen1;
                            });
                          }
                        },
                        icon: const Icon(Icons.share),
                      ),
                      IconButton(
                        onPressed: () => setState(() {
                          this.widget.viewModeService.Screen =
                              TheScreen.Screen1;
                        }),
                        icon: const Icon(Icons.close),
                      ),
                    ],
                  ),
                ),
                Spacer(),
                Align(
                  alignment: Alignment.topRight,
                  child: Padding(
                    padding: const EdgeInsets.only(right: 16),
                    child: IconButton(
                      onPressed: () async {
                        if (await this.widget.moodService.KeepMoodForNow(
                            this.widget.moodService.Token, this.mood!)) {
                          setState(() {
                            this.widget.viewModeService.Screen =
                                TheScreen.Screen1;
                          });
                        }
                      },
                      icon: const Icon(Icons.save),
                    ),
                  ),
                ),
                SizedBox(
                  height: 40,
                ),
              ],
            )),
      ),
    );
  }
}
