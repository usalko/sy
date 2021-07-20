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

import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:frontend/src/component/error_message_widget.dart';
import 'package:frontend/src/component/square_widget.dart';
import 'package:frontend/src/component/triangle_widget.dart';
import 'package:frontend/src/model/geometry_shape.dart';
import 'package:frontend/src/model/mood.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';
import 'package:frontend/src/service/mood_service.dart';
import 'package:frontend/src/service/view_mode_service.dart';
import 'package:intl/intl.dart';

import 'api_is_disabled_widget.dart';
import 'circle_widget.dart';

class HistoryWidget extends StatefulWidget {
  HistoryWidget(
      {Key? key, required this.moodService, required this.viewModeService})
      : super(key: key);

  final MoodService moodService;
  final ViewModeService viewModeService;

  @override
  _HistoryWidgetState createState() => _HistoryWidgetState();
}

class _HistoryWidgetState extends State<HistoryWidget> {
  late Future<List<Mood>> history;

  @override
  void initState() {
    super.initState();
    this.history =
        this.widget.moodService.getHistory(this.widget.moodService.token);
    widget.viewModeService.screenChangeEvent.subscribe((args) {
      if (args is ScreenChangeArgs) {
        this.setState(() {
          this.history =
              this.widget.moodService.getHistory(this.widget.moodService.token);
        });
      }
    });
  }

  @override
  void dispose() {
    widget.viewModeService.screenChangeEvent.unsubscribeAll();
    widget.moodService.apiDisableEvent.unsubscribeAll();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return getHistory(context, this.widget.viewModeService.screen);
  }

  Widget getHistory(BuildContext context, TheScreen? screen) {
    if (this.widget.moodService.isApiDisabled) {
      return ApiIsDisabledWidget();
    }

    return FutureBuilder<List<Mood>>(
      future: this.history,
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          if (screen == TheScreen.Screen1) {
            return ListView(
              children:
                  snapshot.data?.map((e) => _moodWidget(e)).toList() ?? [],
            );
          }
          return Padding(
            padding: EdgeInsets.only(left: 20),
            child: ListView(
              scrollDirection: Axis.horizontal,
              children:
                  snapshot.data?.map((e) => _moodWidget(e)).toList() ?? [],
            ),
          );
        } else if (snapshot.hasError) {
          if (this.widget.moodService.isApiDisabled) {
            return ApiIsDisabledWidget();
          }
          return ErrorMessageWidget("${snapshot.error}");
        }

        // By default, show a loading spinner.
        return Padding(
            padding: EdgeInsets.all(6),
            child: CircularProgressIndicator(
              color: Theme.of(context).backgroundColor,
            ));
      },
    );
  }

  Widget _moodWidget(Mood mood) {
    if (mood.kind == GeometryShape.Triangle) {
      return Container(
        child: Center(
            child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          TriangleWidget(
              width: 12,
              color: Theme.of(context).accentColor,
              content: mood.content),
          Text(DateFormat('dd/MM/yyyy').format(mood.created!))
        ])),
      );
    } else if (mood.kind == GeometryShape.Square) {
      return Container(
        child: Center(
            child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          SquareWidget(
              width: 12,
              color: Theme.of(context).accentColor,
              content: mood.content),
          Text(DateFormat('dd/MM/yyyy').format(mood.created!))
        ])),
      );
    } else if (mood.kind == GeometryShape.Circle) {
      return Container(
        child: Center(
            child: Row(mainAxisAlignment: MainAxisAlignment.center, children: [
          CircleWidget(
              width: 12,
              color: Theme.of(context).accentColor,
              content: mood.content),
          Text(DateFormat('dd/MM/yyyy').format(mood.created!))
        ])),
      );
    }
    throw new UnimplementedError();
  }
}
