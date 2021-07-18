import 'dart:math';

import 'package:frontend/src/model/mood.dart';

import 'package:event/event.dart';
import 'package:frontend/src/model/api_disable_args.dart';
import 'package:frontend/src/service/mood_service_adapter.dart';
import 'package:frontend/src/service/response_processor.dart';
import 'package:shared_preferences/shared_preferences.dart';

const DEFAULT_VALUE_FOR_IS_API_DISABLED = false;
const API_ENDPOINT = const String.fromEnvironment('API_ENDPOINT',
    defaultValue: 'http://localhost:8080/api');

/// Represents a global application state
class MoodService {
  bool _isApiDisabled = DEFAULT_VALUE_FOR_IS_API_DISABLED;
  Future<String>? _token;

  final apiDisableEvent = Event<ApiDisableArgs>();

  Future<String> get token => _token ?? this.requestToken();

  set isApiDisabled(bool value) {
    this._isApiDisabled = value;
    this.apiDisableEvent.broadcast(ApiDisableArgs(value));
  }

  bool get isApiDisabled {
    return this._isApiDisabled;
  }

  Future<List<Mood>> getSharedMoods() async {
    if (isApiDisabled) {
      return Future.value([]);
    }
    final response = await MoodServiceAdapter()
        .get(Uri.parse('$API_ENDPOINT/GetSharedMoods'));
    return ResponseProcessor(response)
        .moodsList(errorMessage: 'Failed to load shared moods');
  }

  Future<List<Mood>> getHistory(Future<String> token) async {
    if (isApiDisabled) {
      return Future.value([]);
    }
    final response = await MoodServiceAdapter()
        .get(Uri.parse('$API_ENDPOINT/GetHistory?token=${await token}'));
    return ResponseProcessor(response)
        .moodsList(errorMessage: 'Failed to load history');
  }

  Future<bool> keepMoodForNow(Future<String> token, Mood mood) async {
    if (isApiDisabled) {
      return Future.value(false);
    }
    final response = await MoodServiceAdapter().post(
        Uri.parse('$API_ENDPOINT/KeepMoodForNow?token=${await token}'),
        body: mood);
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to keep mood for now');
  }

  Future<bool> shareMood(Future<String> token, Mood mood) async {
    if (isApiDisabled) {
      return Future.value(false);
    }
    final response = await MoodServiceAdapter().post(
        Uri.parse('$API_ENDPOINT/ShareMood?token=${await token}'),
        body: mood);
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to share mood');
  }

  Future<String> requestToken() async {
    if (isApiDisabled) {
      return '1';
    }
    var sharedPreferenses = await SharedPreferences.getInstance();
    var localToken = sharedPreferenses.getString('token');
    if (localToken != null &&
        localToken.length == 36 &&
        String.fromCharCodes((await MoodServiceAdapter().get(Uri.parse(
          '$API_ENDPOINT/Token/Validation?token=$localToken'))).bodyBytes) == 'true'
        ) {
      return localToken;
    }
    var randomGenerator = Random(DateTime.now().microsecondsSinceEpoch);
    int userAgentHash = randomGenerator.nextInt(4294967295).toSigned(32);
    int randomSeed = randomGenerator.nextInt(4294967295).toSigned(32);
    final response = await MoodServiceAdapter().get(
      Uri.parse(
          '$API_ENDPOINT/Token?user-agent-hash=$userAgentHash&seed=$randomSeed'),
    );
    if (response.statusCode == 200) {
      var remoteToken = response.body;
      sharedPreferenses.setString('token', remoteToken);
      return remoteToken;
    }
    throw Exception('Failed to request token');
  }
}
