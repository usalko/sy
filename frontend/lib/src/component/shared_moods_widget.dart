import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/scheduler.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/geometry.dart';
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

    var size = MediaQuery.of(context).size;

    var sharedMoods = getSharedMoods(context);
    if (this.widget.viewModeService.Screen == TheScreen.Screen1) {
      return Column(
        children: <Widget>[
          Flexible(
            child: GridView.count(
              primary: false,
              padding: const EdgeInsets.all(20),
              crossAxisSpacing: 0,
              mainAxisSpacing: 0,
              crossAxisCount: (size.width / 44).round(),
              children: sharedMoods,
            ),
          )
        ],
      );
    }
    return Padding(
        padding: const EdgeInsets.all(20),
        child: Container(
            key: stickyKey,
            height: 44.0,
            child: SizedBox(
              width: 44.0 * sharedMoods.length,
            ) // Covered object anticipation
            ));
  }

  Widget stickyBuilder(BuildContext context) {
    var sharedMoods = getSharedMoods(context);

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
                  children: sharedMoods,
                ),
              ),
            ),
          );
        }
        return Container();
      },
    );
  }

  List<Widget> getSharedMoods(BuildContext context) {
    return [
      Container(
        child: Center(
            child: TriangleWidget(
                width: 44,
                showBorder: false,
                content: <Geometry?>[
              Geometry.triangle(Colors.blueGrey.value),
              null,
              Geometry.square(Colors.redAccent.value),
              null,
              Geometry.circle(Colors.greenAccent.value)
            ])),
      ),
      Container(
        child: Center(
            child: TriangleWidget(
                width: 44,
                showBorder: false,
                content: <Geometry?>[
              Geometry.triangle(Colors.blueGrey.value),
              null,
              Geometry.square(Colors.redAccent.value),
              null,
              Geometry.circle(Colors.greenAccent.value)
            ])),
      ),
      Container(
        child: Center(
            child: SquareWidget(
          width: 44,
          showBorder: false,
          content: <Geometry?>[
            Geometry.circle(Colors.blueGrey.value),
            null,
            Geometry.triangle(Colors.redAccent.value),
            null,
            Geometry.square(Colors.greenAccent.value)
          ],
        )),
      ),
      Container(
        child: Center(
            child: SquareWidget(
          width: 44,
          showBorder: false,
          content: <Geometry?>[
            Geometry.square(Colors.blueGrey.value),
            null,
            Geometry.circle(Colors.redAccent.value),
            null,
            Geometry.triangle(Colors.greenAccent.value)
          ],
        )),
      ),
      Container(
        child: Center(
            child: TriangleWidget(
                width: 44,
                showBorder: false,
                content: <Geometry?>[
              Geometry.triangle(Colors.blueGrey.value),
              null,
              Geometry.square(Colors.redAccent.value),
              null,
              Geometry.circle(Colors.greenAccent.value)
            ])),
      ),
      Container(
        child: Center(
            child: SquareWidget(
          width: 44,
          showBorder: false,
          content: <Geometry?>[
            Geometry.square(Colors.blueGrey.value),
            null,
            Geometry.circle(Colors.redAccent.value),
            null,
            Geometry.triangle(Colors.greenAccent.value)
          ],
        )),
      ),
    ];
  }
}
