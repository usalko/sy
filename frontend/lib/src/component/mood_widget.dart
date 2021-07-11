import 'package:flutter/cupertino.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

class MoodWidget extends StatefulWidget {
  MoodWidget(
      {Key? key,
      required this.moodService,
      required this.viewModeService})
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

    return Row(
      children: [
        
      ],
    );
  }
}
