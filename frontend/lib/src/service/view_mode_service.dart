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

import 'package:event/event.dart';
import 'package:frontend/src/model/screen_change_args.dart';
import 'package:frontend/src/model/the_screen.dart';

const DEFAULT_SCREEN = TheScreen.Screen1;

/// Represents a global application state
class ViewModeService {
  TheScreen? _screen = DEFAULT_SCREEN;

  final screenChangeEvent = Event<ScreenChangeArgs>();

  set screen(TheScreen? value) {
    this._screen = value;
    this.screenChangeEvent.broadcast(ScreenChangeArgs(value));
  }

  TheScreen? get screen {
    return this._screen;
  }
}
