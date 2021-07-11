///https://pub.dev/packages/event
import 'package:event/event.dart';
import 'package:frontend/src/model/the_screen.dart';

///
class ScreenChangeArgs extends EventArgs {
  TheScreen? value;

  ScreenChangeArgs(this.value);
}
