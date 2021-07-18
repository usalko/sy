package io.github.usalko.sy.repository;

import io.github.usalko.sy.model.Token;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TokenRepository extends PagingAndSortingRepository<Token, String> {
}