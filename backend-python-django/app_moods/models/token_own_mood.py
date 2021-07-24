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

from app_tokens.models.token import Token
from django.db import models

from .mood import Mood
from .own_mood import OwnMood


class TokenOwnMood(models.Model):

    token = models.ForeignKey(
        Token, db_column='token_id', on_delete=models.CASCADE)
    own_mood = models.ForeignKey(
        OwnMood, db_column='own_mood_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'token_own_moods'
        unique_together = (('token', 'own_mood'),)
        app_label = 'app_moods'
