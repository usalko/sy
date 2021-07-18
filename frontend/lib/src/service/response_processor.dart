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
    result['created'] = element['dateCreated'];
    result['kind'] = element['geometryShape']?['mnemonic'];
    result['content'] = element['moodGeometryShapes']
        ?.map((e) => processGeometryForSpringPlatform(e))
        .toList();
    return result;
  }

  Map<String, dynamic> processGeometryForSpringPlatform(
      Map<String, dynamic> element) {
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
