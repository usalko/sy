from django.db import models
from app_tokens.models.token import Token
from .own_mood import OwnMood
from .mood import Mood


class TokenOwnMood(Mood):

    token = models.ForeignKey(
        Token, db_column='token_id', on_delete=models.CASCADE)
    own_mood = models.ForeignKey(
        OwnMood, db_column='own_mood_id', on_delete=models.CASCADE)

    class Meta:
        db_table = 'token_own_moods'
        unique_together = (('token', 'own_mood'),)
        app_label = 'app_moods'
