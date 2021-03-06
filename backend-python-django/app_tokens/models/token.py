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

from uuid import uuid4
from django.db import models


class Token(models.Model):
    id = models.UUIDField(primary_key=True, default=uuid4, editable=False)
    created = models.DateTimeField(help_text='Timestamp when token created')

    @staticmethod
    def of(token: str):
        result = Token()
        result.id = token
        return result

    class Meta:
        db_table = 'tokens'
        app_label = 'app_tokens'
