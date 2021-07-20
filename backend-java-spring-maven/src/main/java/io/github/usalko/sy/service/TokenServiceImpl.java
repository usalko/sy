// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
package io.github.usalko.sy.service;

import io.github.usalko.sy.model.Token;
import io.github.usalko.sy.repository.TokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    private final ThreadLocal<SecureRandom> secureRandomThreadLocal;

    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
        this.secureRandomThreadLocal = new ThreadLocal<>();
    }

    @Override
    public Optional<Token> getToken(String token) {
        return this.tokenRepository.findById(token);
    }

    @Override
    public Token generate(int userAgentHash, int randomSeed) {
        if (this.secureRandomThreadLocal.get() == null) {
            this.secureRandomThreadLocal.set(new SecureRandom());
        }
        ByteBuffer seed = ByteBuffer.allocate(8);
        seed.putInt(userAgentHash);
        seed.putInt(randomSeed);
        this.secureRandomThreadLocal.get().setSeed(seed.array());
        byte[] uuid = new byte[16];
        this.secureRandomThreadLocal.get().nextBytes(uuid);

        return this.tokenRepository.save(new Token(UUID.nameUUIDFromBytes(uuid).toString()));
    }

    @Override
    public boolean isValid(String token) {
        return this.tokenRepository.existsById(token);
    }
}
