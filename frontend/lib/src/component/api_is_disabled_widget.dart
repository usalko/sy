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

import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class ApiIsDisabledWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return new Padding(
        padding: const EdgeInsets.all(6),
        child: new Column(
          children: [
            Text(
              'API is disabled',
              style: TextStyle(
                  fontWeight: FontWeight.w700,
                  fontSize: 22,
                  color: Theme.of(context).disabledColor),
            ),
            Text('For demo request mail to: ivict@rambler.ru',
                style: TextStyle(
                    fontSize: 10, color: Theme.of(context).disabledColor))
          ],
        ));
  }
}
