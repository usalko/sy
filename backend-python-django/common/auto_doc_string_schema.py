from yaml import safe_load
from yaml.scanner import ScannerError
from rest_framework.schemas.openapi import AutoSchema
from coreapi.document import Link


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
                self._path_methods_docs[key] = safe_load(method_docstring)
            except ScannerError:
                self._path_methods_docs[key] = {}
        return self._path_methods_docs[key]

    def get_components(self, path, method):
        components = super().get_components(path, method)
        components.update(self._documentation(path, method).get('components', {}))
        return components

    def get_operation(self, path, method):
        operation = super().get_operation(path, method)
        operation.update(self._documentation(path, method).get(method.lower(), {}))
        return operation
