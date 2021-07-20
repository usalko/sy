// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:frontend/src/service/request_processor.dart';
import 'package:http/browser_client.dart';
import 'package:http/http.dart' as http;
import 'package:http/retry.dart';

const INTEGRATION_TEST_MODE =
    const bool.fromEnvironment('INTEGRATION_TEST_MODE', defaultValue: false);

class MoodServiceAdapter {
  static final _client = RetryClient(BrowserClient());
  const MoodServiceAdapter();

  Future<http.Response> get(Uri url, {Map<String, String>? headers}) {
    if (INTEGRATION_TEST_MODE) {
      return integrationTestSupport(url, headers);
    }
    return _client.get(url, headers: headers);
  }

  Future<http.Response> post(Uri url,
      {Map<String, String>? headers, Object? body, Encoding? encoding}) {
    if (INTEGRATION_TEST_MODE) {
      return integrationTestSupport(url, headers);
    }
    return RequestProcessor(_client).post(url, headers, body, encoding);
  }

  Future<http.Response> integrationTestSupport(
      Uri url, Map<String, String>? headers) {
    return Future.delayed(Duration(seconds: 5), () {
      if (url.path.contains('GetSharedMoods')) {
        return http.Response.bytes(
            utf8.encode(jsonEncode([
              Mood.square([
                Geometry.triangle(Colors.red.value),
                null,
                Geometry.square(Colors.green.value),
                null,
                Geometry.circle(Colors.blue.value)
              ]).toJson()
            ])),
            200);
      }
      if (url.path.contains('GetHistory')) {
        return http.Response.bytes(
            utf8.encode(jsonEncode([
              Mood.square([
                Geometry.triangle(Colors.red.value),
                null,
                Geometry.square(Colors.green.value),
                null,
                Geometry.circle(Colors.blue.value)
              ], created: DateTime.now())
                  .toJson()
            ])),
            200);
      }
      if (url.path.contains('KeepMoodForNow')) {
        return http.Response.bytes(utf8.encode(jsonEncode(['Ok'])), 200);
      }
      if (url.path.contains('ShareMood')) {
        return http.Response.bytes(utf8.encode(jsonEncode(['Ok'])), 200);
      }
      throw new UnimplementedError();
    });
  }
}
