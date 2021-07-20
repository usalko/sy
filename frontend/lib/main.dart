import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/history_widget.dart';
import 'package:frontend/src/component/mood_widget.dart';
import 'package:frontend/src/component/shared_moods_widget.dart';
import 'package:frontend/src/model/api_disable_args.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';

void main() {
  runApp(SyApp());
}

class SyApp extends StatelessWidget {
  static const MaterialColor syPrimaryColor = MaterialColor(
    _syColorPrimaryValue,
    <int, Color>{
      50: Color(0xFFEFE5FD),
      100: Color(0xFFD4BFF9),
      200: Color(0xFFB794F6),
      300: Color(0xFF9965F4),
      400: Color(0xFF7E3FF2),
      500: Color(_syColorPrimaryValue),
      600: Color(0xFF5300E8),
      700: Color(0xFF3D00E0),
      800: Color(0xFF1C00DB),
      900: Color(0xFF0000D6),
    },
  );
  static const int _syColorPrimaryValue = 0xFF6002EE;

  static const MaterialColor sySecondaryColor = MaterialColor(
    _syColorSecondaryValue,
    <int, Color>{
      50: Color(0xFFF2FDE4),
      100: Color(0xFFDEFABB),
      200: Color(0xFFC6F68D),
      300: Color(0xFFAAF255),
      400: Color(_syColorSecondaryValue),
      500: Color(0xFF75E900),
      600: Color(0xFF61D800),
      700: Color(0xFF41C300),
      800: Color(0xFF09FF00),
      900: Color(0xFF008B00),
    },
  );
  static const int _syColorSecondaryValue = 0xFF90EE02;

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return GestureDetector(
      onTap: () {
        FocusScopeNode currentFocus = FocusScope.of(context);

        if (!currentFocus.hasPrimaryFocus) {
          currentFocus.unfocus();
        }
      },
      child: MaterialApp(
        title: 'Share yours mood with anyone',
        theme: ThemeData(
          primarySwatch: syPrimaryColor,
          accentColor: sySecondaryColor,
          visualDensity: VisualDensity.adaptivePlatformDensity,
          fontFamily: 'TexGyreHeros',
          scrollbarTheme: ScrollbarThemeData(
              isAlwaysShown: true,
              thickness: MaterialStateProperty.all(12.0),
              showTrackOnHover: false,
              radius: Radius.zero),
        ),
        home: HomePage(
          title: 'Share yours mood with anyone',
          moodService: MoodService(),
          viewModeService: ViewModeService(),
        ),
        debugShowCheckedModeBanner: false,
      ),
    );
  }
}

class HomePage extends StatefulWidget {
  HomePage(
      {Key? key,
      required this.title,
      required this.moodService,
      required this.viewModeService})
      : super(key: key);

  final String title;
  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _HomePageState createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final minWidth = 740.0;
  final minHeight = 880.0;

  ScrollController _horizontalController = ScrollController();
  ScrollController _verticalController = ScrollController();

  @override
  void initState() {
    super.initState();
    widget.moodService.apiDisableEvent.subscribe((args) {
      if (args is ApiDisableArgs) {
        this.setState(() {});
      }
    });
    widget.viewModeService.screenChangeEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {});
      }
    });
  }

  @override
  void dispose() {
    _horizontalController.dispose();
    _verticalController.dispose();
    super.dispose();
  }

  Widget _buildScaffold() {
    return Scaffold(
      body: Container(
          child: Column(children: [
        Flexible(
          flex: 1,
          child: SharedMoodsWidget(
              moodService: widget.moodService,
              viewModeService: widget.viewModeService),
        ),
        Expanded(
          flex: widget.viewModeService.screen == TheScreen.Screen1 ? 1 : 6,
          child: MoodWidget(
              moodService: widget.moodService,
              viewModeService: widget.viewModeService),
        ),
        Flexible(
          flex: 1,
          child: HistoryWidget(
              moodService: widget.moodService,
              viewModeService: widget.viewModeService),
        ),
      ])),
    );
  }

  @override
  Widget build(BuildContext context) {
    final scaffold = _buildScaffold();

    if (kDebugMode) {
      return Banner(
          location: BannerLocation.bottomEnd,
          color: Colors.green,
          message: '\u{1F525}\u{1F525}\u{1F525}\u{1F525}\u{1F525}\u{1F525}',
          child: scaffold);
    }
    return scaffold;
  }
}
