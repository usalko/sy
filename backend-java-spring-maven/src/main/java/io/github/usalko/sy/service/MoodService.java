package io.github.usalko.sy.service;

import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface MoodService {

    @NotNull Iterable<? extends Mood> getSharedMoods(int limit);

    @NotNull Iterable<? extends Mood> getOwnMoods(@NotNull(message = "The token cannot be null.") String token,
                                                  int limit);

    void share(@NotNull(message = "The token cannot be null.") String token,
               @NotNull(message = "The mood cannot be null.") @Valid SharedMood mood);

    void keep(@NotNull(message = "The token cannot be null.") String token,
              @NotNull(message = "The mood cannot be null.") @Valid OwnMood mood);
}