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

class MoodActionsWidget extends StatelessWidget {
  final VoidCallback? onShare;
  final VoidCallback? onClose;
  final VoidCallback? onKeepForNow;

  const MoodActionsWidget(
      {Key? key, this.onShare, this.onClose, this.onKeepForNow})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 130,
      child: Padding(
        padding: const EdgeInsets.all(0),
        child: Card(
            color: Theme.of(context).canvasColor,
            elevation: 0,
            child: Column(
              children: [
                Padding(
                  padding: const EdgeInsets.only(top: 16),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      IconButton(
                        onPressed: onShare,
                        icon: const Icon(Icons.share),
                      ),
                      IconButton(
                        onPressed: onClose,
                        icon: const Icon(Icons.close),
                      ),
                    ],
                  ),
                ),
                Spacer(),
                Align(
                  alignment: Alignment.topRight,
                  child: Padding(
                    padding: const EdgeInsets.only(right: 22),
                    child: IconButton(
                      onPressed: onKeepForNow,
                      icon: const Icon(Icons.save),
                    ),
                  ),
                ),
                SizedBox(
                  height: 40,
                ),
              ],
            )),
      ),
    );
  }
}
