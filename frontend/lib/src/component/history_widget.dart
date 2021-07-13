import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

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

  @override
  void initState() {
    sticky?.remove();
    this.sticky = OverlayEntry(
      builder: (context) => stickyBuilder(context),
    );

    SchedulerBinding.instance?.addPostFrameCallback((_) {
      Overlay.of(context)?.insert(this.sticky!);
    });

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
        });
      }
    });

    var history = getHistory(context);
    if (this.widget.viewModeService.Screen == TheScreen.Screen1) {
      return Column(
        children: <Widget>[
          Flexible(
            child: ListView(
              children: history,
            ),
          )
        ],
      );
    }
    return Container(
      key: stickyKey,
      height: 22.0,
      child: Text("w" * (history.length * (8 + 1))), // Covered object anticipation
    );
  }

  Widget stickyBuilder(BuildContext context) {
    var history = getHistory(context);

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
                child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: history,
                ),
              ),
            ),
          );
        }
        return Container();
      },
    );
  }

  List<Widget> getHistory(BuildContext context) {
    return [
      Container(
        child: Center(
            child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [TriangleWidget(width: 12, color: Theme.of(context).accentColor), Text('13/04/2021')])),
      ),
      Container(
        child: Center(
            child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [SquareWidget(width: 12, color: Theme.of(context).accentColor), Text('12/04/2021')])),
      ),
    ];
  }
}
