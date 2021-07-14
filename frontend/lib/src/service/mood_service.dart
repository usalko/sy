import 'dart:convert';

import 'package:frontend/src/model/mood.dart';

import 'package:event/event.dart';
import 'package:frontend/src/model/api_disable_args.dart';
import 'package:frontend/src/service/request_adapter.dart';

const DEFAULT_VALUE_FOR_IS_API_DISABLED = false;
const API_ENDPOINT = const String.fromEnvironment('API_ENDPOINT', defaultValue: 'http://localhost:8080/api');

/// Represents a global application state
class MoodService {
  bool _isApiDisabled = DEFAULT_VALUE_FOR_IS_API_DISABLED;
  String? _token;

  final apiDisableEvent = Event<ApiDisableArgs>();

  String get Token => _token ?? '1';

  set IsApiDisabled(bool value) {
    this._isApiDisabled = value;
    this.apiDisableEvent.broadcast(ApiDisableArgs(value));
  }

  bool get IsApiDisabled {
    return this._isApiDisabled;
  }

  Future<List<Mood>> getSharedMoods() async {
    if (IsApiDisabled) {
      return Future.value([]);
    }
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/GetSharedMoods'));
    if (response.statusCode == 200) {
      return (jsonDecode(response.body) as List<dynamic>).map((e) => Mood.fromJson(e)).toList();
    }
    throw Exception('Failed to load shared moods');
  }

  Future<List<Mood>> getHistory(String token) async {
    if (IsApiDisabled) {
      return Future.value([]);
    }
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/GetHistory?token=$token'));
    if (response.statusCode == 200) {
      return (jsonDecode(response.body) as List<dynamic>).map((e) => Mood.fromJson(e)).toList();
    }
    throw Exception('Failed to load history');
  }

  Future<bool> KeepMoodForNow(String token, Mood mood) async {
    if (IsApiDisabled) {
      return Future.value(false);
    }
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/KeepMoodForNow?token=$token&mood=${Uri.encodeQueryComponent(json.encode(mood.toJson()))}'));
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to keep mood for now');
  }

  Future<bool> ShareMood(String token, Mood mood) async {
    if (IsApiDisabled) {
      return Future.value(false);
    }
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/ShareMood?token=$token&mood=${Uri.encodeQueryComponent(json.encode(mood.toJson()))}'));
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to share mood');
  }
}
