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
import 'dart:io';

import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:http/http.dart' as http;

class RequestProcessor {
  final http.Client client;

  RequestProcessor(this.client);

  Future<http.Response> post(
      Uri url, Map<String, String>? headers, Object? body, Encoding? encoding) {
    var requestHeaders = headers ?? {};
    requestHeaders[HttpHeaders.contentTypeHeader] =
        'application/json; charset=UTF-8';
    if (body is Mood) {
      requestHeaders['pragma'] = 'Backend-Platform-Identity=Spring;';
      return this.client.post(url,
          headers: requestHeaders,
          body: jsonEncode(processMoodForSpringPlatform(body)),
          encoding: encoding);
    }
    return this
        .client
        .post(url, headers: requestHeaders, body: body, encoding: encoding);
  }

  Map<String, dynamic> processMoodForSpringPlatform(Mood mood) {
    var result = Map<String, dynamic>();
    result['id'] = int.tryParse(mood.id);
    result['created'] = mood.created;
    result['geometryShape'] = {'mnemonic': GeometryShapeExt.toJson(mood.kind)};
    result['moodGeometryShapes'] =
        mood.content.map((e) => processGeometryForSpringPlatform(e)).toList();
    return result;
  }

  Map<String, dynamic>? processGeometryForSpringPlatform(Geometry? geometry) {
    if (geometry == null) {
      return null;
    }
    var result = Map<String, dynamic>();
    result['geometryShape'] = {
      'mnemonic': GeometryShapeExt.toJson(geometry.shape)
    };
    result['color'] = geometry.color.toSigned(32);
    return result;
  }
}
