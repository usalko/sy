# Sy (Share your mood with anyone)
# Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program.  If not, see <http:#www.gnu.org/licenses/>.

from rest_framework.schemas.openapi import AutoSchema
from yaml import safe_load
from yaml.scanner import ScannerError


class AutoDocStringSchema(AutoSchema):

    def __init__(self, tags=None, operation_id_base=None, component_name=None):
        super().__init__(tags=tags, operation_id_base=operation_id_base,
                         component_name=component_name)
        self._path_methods_docs = dict()

    def _documentation(self, path, method):
        key = (path, method)
        if self._path_methods_docs.get(key) is None:
            method_name = getattr(self.view, 'action', method.lower())
            method_docstring = getattr(self.view, method_name, None).__doc__
            try:
                result = safe_load(method_docstring)
                if isinstance(result, str):
                    result = {method: {'description': result}}
                self._path_methods_docs[key] = result
            except ScannerError:
                self._path_methods_docs[key] = {}
        return self._path_methods_docs[key]

    def get_components(self, path, method):
        components = super().get_components(path, method)
        components.update(self._documentation(
            path, method).get('components', {}))
        return components

    def get_operation(self, path, method):
        operation = super().get_operation(path, method)
        operation.update(self._documentation(
            path, method).get(method.lower(), {}))
        return operation
