import 'dart:convert';

import 'package:frontend/src/model/mood.dart';

import 'package:event/event.dart';
import 'package:frontend/src/model/api_disable_args.dart';
import 'package:frontend/src/service/mood_service_adapter.dart';
import 'package:frontend/src/service/response_processor.dart';

const DEFAULT_VALUE_FOR_IS_API_DISABLED = false;
const API_ENDPOINT = const String.fromEnvironment('API_ENDPOINT',
    defaultValue: 'http://localhost:8080/api');

/// Represents a global application state
class MoodService {
  bool _isApiDisabled = DEFAULT_VALUE_FOR_IS_API_DISABLED;
  String? _token;

  final apiDisableEvent = Event<ApiDisableArgs>();

  String get token => _token ?? '1';

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

  Future<List<Mood>> getHistory(String token) async {
    if (isApiDisabled) {
      return Future.value([]);
    }
    final response = await MoodServiceAdapter()
        .get(Uri.parse('$API_ENDPOINT/GetHistory?token=$token'));
    return ResponseProcessor(response)
        .moodsList(errorMessage: 'Failed to load history');
  }

  Future<bool> keepMoodForNow(String token, Mood mood) async {
    if (isApiDisabled) {
      return Future.value(false);
    }
    final response = await MoodServiceAdapter().post(
        Uri.parse('$API_ENDPOINT/KeepMoodForNow?token=$token'),
        body: mood);
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to keep mood for now');
  }

  Future<bool> shareMood(String token, Mood mood) async {
    if (isApiDisabled) {
      return Future.value(false);
    }
    final response = await MoodServiceAdapter().post(
        Uri.parse('$API_ENDPOINT/KeepMoodForNow?token=$token'),
        body: mood);
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to share mood');
  }
}
