import 'package:flutter/cupertino.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

class HistoryWidget extends StatefulWidget {
  HistoryWidget(
      {Key? key,
      required this.moodService,
      required this.viewModeService})
      : super(key: key);

  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _HistoryWidgetState createState() => _HistoryWidgetState();

}

class _HistoryWidgetState extends State<HistoryWidget> {
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
