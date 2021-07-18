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
