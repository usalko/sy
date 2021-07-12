import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

import 'circle_widget.dart';

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
      var cardWidth = size.width / 3 - 10;
      cards = [
        Card(
          child: TriangleWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            editMode: true,
          ),
        ),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Square) {
      var cardWidth = size.width / 3 - 10;
      cards = [
        Card(
          child: SquareWidget(
            width: cardWidth,
            color: Theme.of(context).dividerColor,
            editMode: true,
          ),
        ),
      ];
    } else if (widget.viewModeService.Screen == TheScreen.Screen2Circle) {
      var cardWidth = size.width / 3 - 10;
      cards = [
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
}
