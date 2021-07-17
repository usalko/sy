import 'package:event/event.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';

const DEFAULT_SCREEN = TheScreen.Screen1;

/// Represents a global application state
class ViewModeService {
  TheScreen? _screen = DEFAULT_SCREEN;

  final screenChangeEvent = Event<ScreenChangeArgs>();

  set screen(TheScreen? value) {
    this._screen = value;
    this.screenChangeEvent.broadcast(ScreenChangeArgs(value));
  }

  TheScreen? get screen {
    return this._screen;
  }
}
