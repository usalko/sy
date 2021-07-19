package io.github.usalko.sy.repository;

import io.github.usalko.sy.model.SharedMood;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SharedMoodRepository extends PagingAndSortingRepository<SharedMood, Long> {
    List<SharedMood> findByOrderByIdDesc(Pageable pageable);
}