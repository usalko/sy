package io.github.usalko.sy.repository;

import io.github.usalko.sy.model.SharedMood;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SharedMoodRepository extends PagingAndSortingRepository<SharedMood, Long> {
}