import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:frontend/src/model/geometry.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:http/http.dart' as http;

class RequestAdapter {
  const RequestAdapter();

  Future<http.Response> request(Uri url, {Map<String, String>? headers}) {
    //return http.get(url, headers: headers);
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
              ], created: DateTime.now()).toJson()
            ])),
            200);
      }
      if (url.path.contains('KeepMoodForNow')) {
        return http.Response.bytes(
            utf8.encode(jsonEncode(['Ok'])),
            200);
      }
      if (url.path.contains('ShareMood')) {
        return http.Response.bytes(
            utf8.encode(jsonEncode(['Ok'])),
            200);
      }
      throw new UnimplementedError();
    });
  }
}
