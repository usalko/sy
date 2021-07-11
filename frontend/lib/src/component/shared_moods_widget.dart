import 'package:flutter/cupertino.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

class SharedMoodsWidget extends StatefulWidget {
  SharedMoodsWidget(
      {Key? key,
      required this.moodService,
      required this.viewModeService})
      : super(key: key);

  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _SharedMoodsWidgetState createState() => _SharedMoodsWidgetState();

}

class _SharedMoodsWidgetState extends State<SharedMoodsWidget> {
  @override
  Widget build(BuildContext context) {
    widget.moodService.apiDisableEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {});
      }
    });

    return Row(
      children: [
        
      ],
    );
  }
}
