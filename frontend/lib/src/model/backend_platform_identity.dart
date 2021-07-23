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

enum BackendPlatformIdentity {
  Spring,
  Django,
}

extension BackendPlatformIdentityExt on BackendPlatformIdentity {
  static const Map<BackendPlatformIdentity, String> keys = {
    BackendPlatformIdentity.Spring: 'backend-platform-identity=spring',
    BackendPlatformIdentity.Django: 'backend-platform-identity=djano',
  };
  static const Map<String, BackendPlatformIdentity> values = {
    'backend-platform-identity=spring': BackendPlatformIdentity.Spring,
    'backend-platform-identity=django': BackendPlatformIdentity.Django,
  };

  String? get key => keys[this];

  static BackendPlatformIdentity fromHeader(String header) {
    var headerInLowerCase = header.toLowerCase();
    return values.entries
        .firstWhere((element) => headerInLowerCase.contains(element.key),
            orElse: () => MapEntry('', BackendPlatformIdentity.Spring))
        .value;
  }
}
