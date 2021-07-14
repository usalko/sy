package io.github.usalko.sy.service;

import io.github.usalko.sy.model.Mood;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface MoodService {

    @NotNull Iterable<Mood> getAllMoods();

    Mood create(@NotNull(message = "The mood cannot be null.") @Valid Mood mood);

    void update(@NotNull(message = "The mood cannot be null.") @Valid Mood mood);
}