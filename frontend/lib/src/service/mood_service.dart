
import 'package:event/event.dart';
import 'package:frontend/src/model/api_disable_args.dart';

final DEFAULT_API_DISABLED = true;

/// Represents a global application state
class MoodService {

  bool? _apiDisabled = DEFAULT_API_DISABLED;

  final apiDisableEvent = Event<ApiDisableArgs>();

  set ApiDisabled (bool? value) {
    this._apiDisabled = value;
    this.apiDisableEvent.broadcast(ApiDisableArgs(value));
  }

  bool? get ApiDisabled {
    return this._apiDisabled;
  }
}