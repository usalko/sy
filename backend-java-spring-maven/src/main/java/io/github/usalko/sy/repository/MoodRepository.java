package io.github.usalko.sy.repository;

import io.github.usalko.sy.model.Mood;
import org.springframework.data.repository.CrudRepository;

public interface MoodRepository extends CrudRepository<Mood, Long> {
}