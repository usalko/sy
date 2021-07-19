import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/api_is_disabled_widget.dart';
import 'package:frontend/src/component/circle_widget.dart';
import 'package:frontend/src/component/error_message_widget.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

class SharedMoodsWidget extends StatefulWidget {
  SharedMoodsWidget(
      {Key? key, required this.moodService, required this.viewModeService})
      : super(key: key);

  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _SharedMoodsWidgetState createState() => _SharedMoodsWidgetState();
}

class _SharedMoodsWidgetState extends State<SharedMoodsWidget> {
  late Future<List<Mood>> sharedMoods;

  @override
  void initState() {
    super.initState();
    this.sharedMoods = this.widget.moodService.getSharedMoods();
    widget.viewModeService.screenChangeEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {
          this.sharedMoods = this.widget.moodService.getSharedMoods();
        });
      }
    });
  }

  @override
  void dispose() {
    widget.viewModeService.screenChangeEvent.unsubscribeAll();
    widget.moodService.apiDisableEvent.unsubscribeAll();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return getSharedMoods(context, this.widget.viewModeService.screen);
  }

  Widget getSharedMoods(BuildContext context, TheScreen? screen) {
    if (this.widget.moodService.isApiDisabled) {
      return ApiIsDisabledWidget();
    }

    return FutureBuilder<List<Mood>>(
      future: this.sharedMoods,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          var size = MediaQuery.of(context).size;
          if (screen == TheScreen.Screen1) {
            return Container(
              padding: const EdgeInsets.only(left: 20, top: 25, right: 20),
              child: GridView.count(
                primary: false,
                crossAxisSpacing: 0,
                mainAxisSpacing: 0,
                crossAxisCount: (size.width / 44).round(),
                children:
                    snapshot.data?.map((e) => _moodWidget(e)).toList() ?? [],
              ),
            );
          }
          return Container(
            padding: const EdgeInsets.only(left: 20, top: 0, right: 20),
            child: ListView(
              scrollDirection: Axis.horizontal,
              children:
                  snapshot.data?.map((e) => _moodWidget(e)).toList() ?? [],
            ),
          );
        } else if (snapshot.hasError) {
          if (this.widget.moodService.isApiDisabled) {
            return ApiIsDisabledWidget();
          }
          return ErrorMessageWidget("${snapshot.error}");
        }

        // By default, show a loading spinner.
        return Padding(
            padding: EdgeInsets.all(6),
            child: CircularProgressIndicator(
              color: Theme.of(context).backgroundColor,
            ));
      },
    );
  }

  Widget _moodWidget(Mood mood) {
    if (mood.kind == GeometryShape.Triangle) {
      return Container(
        child: Center(
            child: TriangleWidget(
                width: 44, showBorder: false, content: mood.content)),
      );
    } else if (mood.kind == GeometryShape.Square) {
      return Container(
        child: Center(
            child: SquareWidget(
                width: 44, showBorder: false, content: mood.content)),
      );
    } else if (mood.kind == GeometryShape.Circle) {
      return Container(
        child: Center(
            child: CircleWidget(
                width: 44, showBorder: false, content: mood.content)),
      );
    }
    throw new UnimplementedError();
  }
}
