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

import 'package:frontend/src/model/mood.dart';
import 'package:http/http.dart' as http;

class ResponseProcessor {
  final http.Response response;

  ResponseProcessor(this.response);

  List<Mood> moodsList({String? errorMessage}) {
    if (response.statusCode == 200) {
      if ((response.headers['pragma'] ?? '')
          .contains('Backend-Platform-Identity=Spring')) {
        return processResponseForSpringPlatform(response.body)
            .map((e) => Mood.fromJson(processMoodForSpringPlatform(e)))
            .toList();
      }
      return (jsonDecode(response.body) as List<dynamic>)
          .map((e) => Mood.fromJson(e))
          .toList();
    }
    throw Exception((errorMessage ?? 'Request is fail') +
        ' // http status: ${response.statusCode}');
  }

  Map<String, dynamic> processMoodForSpringPlatform(
      Map<String, dynamic> element) {
    var result = Map<String, dynamic>();
    result['id'] = "${element['id']}";
    result['created'] = element['created'];
    result['kind'] = element['geometryShape']?['mnemonic'];
    result['content'] = element['moodGeometryShapes']
        ?.map(
          (e) => processGeometryForSpringPlatform(e),
        )
        .toList();
    return result;
  }

  Map<String, dynamic>? processGeometryForSpringPlatform(
      Map<String, dynamic>? element) {
    if (element == null) {
      return null;
    }
    var result = Map<String, dynamic>();
    result['shape'] = element['geometryShape']?['mnemonic'];
    result['color'] = (element['color'] as int).toUnsigned(32);
    return result;
  }

  List<dynamic> processResponseForSpringPlatform(String body) {
    var jsonedResponse = jsonDecode(response.body);
    // Detect pagination
    if (jsonedResponse is Map && jsonedResponse['content'] is List) {
      return jsonedResponse['content'];
    }
    // Detect array
    if (jsonedResponse is List) {
      return jsonedResponse;
    }
    throw UnimplementedError();
  }
}
