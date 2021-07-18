package io.github.usalko.sy.service;

import io.github.usalko.sy.model.Token;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface TokenService {

    Optional<Token> getToken(String token);

    Token generate(int userAgentHash, int randomSeed);

    boolean isValid(String token);
}