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

from .mood_geometry_shape import MoodGeometryShape
from .shared_mood import SharedMood


class SharedMoodGeometryShape(MoodGeometryShape):

    shared_mood = models.ForeignKey(
        SharedMood, db_column='shared_mood_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'shared_mood_geometry_shapes'
        unique_together = (('shared_mood', 'index_in_list', 'geometry_shape'),)
        app_label = 'app_moods'
