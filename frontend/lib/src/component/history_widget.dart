import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:frontend/src/component/error_message_widget.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';
import 'package:intl/intl.dart';

import 'api_is_disabled_widget.dart';
import 'circle_widget.dart';

class HistoryWidget extends StatefulWidget {
  HistoryWidget(
      {Key? key, required this.moodService, required this.viewModeService})
      : super(key: key);

  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _HistoryWidgetState createState() => _HistoryWidgetState();
}

class _HistoryWidgetState extends State<HistoryWidget> {
  final controller = ScrollController();
  // @Explanation: https://stackoverflow.com/questions/49307677/how-to-get-height-of-a-widget
  //               https://github.com/flutter/flutter/issues/168
  OverlayEntry? sticky;
  GlobalKey stickyKey = GlobalKey();
  GlobalKey listViewKey = GlobalKey();

  late Future<List<Mood>> history;

  @override
  void initState() {
    sticky?.remove();
    this.sticky = OverlayEntry(
      builder: (context) => stickyBuilder(context),
    );

    SchedulerBinding.instance?.addPostFrameCallback((_) {
      Overlay.of(context)?.insert(this.sticky!);
    });

    this.history =
        this.widget.moodService.getHistory(this.widget.moodService.token);

    super.initState();
  }

  @override
  void dispose() {
    sticky?.remove();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    widget.viewModeService.screenChangeEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {
          sticky?.remove();
          this.sticky = OverlayEntry(
            builder: (context) => stickyBuilder(context),
          );

          SchedulerBinding.instance?.addPostFrameCallback((_) {
            Overlay.of(context)?.insert(this.sticky!);
          });

          this.history =
              this.widget.moodService.getHistory(this.widget.moodService.token);
        });
      }
    });

    if (this.widget.viewModeService.screen == TheScreen.Screen1) {
      return Column(
        children: <Widget>[
          Flexible(
            child: getHistory(context, this.widget.viewModeService.screen),
          )
        ],
      );
    }
    return Container(
        key: stickyKey,
        height: 22.0,
        child: SizedBox(
          width: 60.0 * 4,
        ) // Covered object anticipation
        );
  }

  Widget stickyBuilder(BuildContext context) {
    return AnimatedBuilder(
      animation: controller,
      builder: (_, Widget? child) {
        final keyContext = stickyKey.currentContext;
        if (keyContext != null) {
          // widget is visible
          final box = keyContext.findRenderObject() as RenderBox;
          final pos = box.localToGlobal(Offset.zero);
          final leftPadding = pos.dx;
          return Positioned(
            top: pos.dy,
            left: leftPadding,
            right: leftPadding,
            height: box.size.height,
            child: Material(
              child: Container(
                key: this.listViewKey,
                alignment: Alignment.center,
                child: getHistory(context, this.widget.viewModeService.screen),
              ),
            ),
          );
        }
        return Container();
      },
    );
  }

  Widget getHistory(BuildContext context, TheScreen? screen) {
    if (this.widget.moodService.isApiDisabled) {
      return ApiIsDisabledWidget();
    }

    return FutureBuilder<List<Mood>>(
      future: this.history,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (screen == TheScreen.Screen1) {
            return ListView(
              children:
                  snapshot.data?.map((e) => _moodWidget(e)).toList() ?? [],
            );
          }
          return ListView(
            scrollDirection: Axis.horizontal,
            children: snapshot.data?.map((e) => _moodWidget(e)).toList() ?? [],
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
            child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          TriangleWidget(
              width: 12,
              color: Theme.of(context).accentColor,
              content: mood.content),
          Text(DateFormat('dd/MM/yyyy').format(mood.created!))
        ])),
      );
    } else if (mood.kind == GeometryShape.Square) {
      return Container(
        child: Center(
            child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          SquareWidget(
              width: 12,
              color: Theme.of(context).accentColor,
              content: mood.content),
          Text(DateFormat('dd/MM/yyyy').format(mood.created!))
        ])),
      );
    } else if (mood.kind == GeometryShape.Circle) {
      return Container(
        child: Center(
            child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          CircleWidget(
              width: 12,
              color: Theme.of(context).accentColor,
              content: mood.content),
          Text(DateFormat('dd/MM/yyyy').format(mood.created!))
        ])),
      );
    }
    throw new UnimplementedError();
  }
}
