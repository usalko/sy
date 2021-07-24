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

from django.db import models


class GeometryShape(models.Model):

    mnemonic = models.CharField(
        max_length=63, help_text='Unique name for geometric shape')

    class Meta:
        db_table = 'geometry_shapes'
        constraints = [
            models.UniqueConstraint(
                fields=['mnemonic'], name='unique shame name')
        ]
        app_label = 'app_moods'
