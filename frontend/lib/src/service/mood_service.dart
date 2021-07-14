import 'dart:convert';

import 'package:frontend/src/model/mood.dart';

import 'package:event/event.dart';
import 'package:frontend/src/model/api_disable_args.dart';
import 'package:frontend/src/service/request_adapter.dart';

final DEFAULT_API_DISABLED = true;
final API_ENDPOINT = const String.fromEnvironment('API_ENDPOINT', defaultValue: 'http://localhost:8080/api');

/// Represents a global application state
class MoodService {
  bool? _apiDisabled = DEFAULT_API_DISABLED;
  String? _token;

  final apiDisableEvent = Event<ApiDisableArgs>();

  String get Token => _token ?? '1';

  set ApiDisabled(bool? value) {
    this._apiDisabled = value;
    this.apiDisableEvent.broadcast(ApiDisableArgs(value));
  }

  bool? get ApiDisabled {
    return this._apiDisabled;
  }

  Future<List<Mood>> getSharedMoods() async {
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/GetSharedMoods'));
    if (response.statusCode == 200) {
      return (jsonDecode(response.body) as List<dynamic>).map((e) => Mood.fromJson(e)).toList();
    }
    throw Exception('Failed to load shared moods');
  }

  Future<List<Mood>> getHistory(String token) async {
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/GetHistory?token=$token'));
    if (response.statusCode == 200) {
      return (jsonDecode(response.body) as List<dynamic>).map((e) => Mood.fromJson(e)).toList();
    }
    throw Exception('Failed to load history');
  }

  Future<bool> KeepModForNow(String token, Mood mood) async {
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/KeepMoodForNow?token=$token&mood=${Uri.encodeQueryComponent(json.encode(mood.toJson()))}'));
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to keep mood for now');
  }

  Future<bool> ShareMood(String token, Mood mood) async {
    final response =
      await RequestAdapter().request(Uri.parse('$API_ENDPOINT/ShareMood?token=$token&mood=${Uri.encodeQueryComponent(json.encode(mood.toJson()))}'));
    if (response.statusCode == 200) {
      return true;
    }
    throw Exception('Failed to share mood');
  }
}
