package io.github.usalko.sy.service;

import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;
import io.github.usalko.sy.model.Token;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface MoodService {

    @NotNull Iterable<SharedMood> getSharedMoods(int limit);

    @NotNull Iterable<OwnMood> getOwnMoods(@NotNull(message = "The token cannot be null.") @Valid Token token,
                                           int limit);

    void share(@NotNull(message = "The mood cannot be null.") @Valid SharedMood mood);

    void keep(@NotNull(message = "The token cannot be null.") @Valid Token token,
              @NotNull(message = "The mood cannot be null.") @Valid OwnMood mood);
}