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

package io.github.usalko.sy.resources;

import io.github.usalko.sy.db.TokenDao;
import io.github.usalko.sy.domain.Token;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/Token")
@Produces(MediaType.APPLICATION_JSON)
public class TokenResource {

    private final TokenDao tokenDao;
    // TODO: rewrite onto service with limited concurrency access
    private final ThreadLocal<SecureRandom> secureRandomThreadLocal;

    @Inject
    public TokenResource(TokenDao tokenDao) {
        this.tokenDao = tokenDao;
        this.secureRandomThreadLocal = new ThreadLocal<>();
    }

    @GET
    public String generate(@QueryParam("user-agent-hash") int userAgentHash,
            @QueryParam("seed") int randomSeed) {
        if (this.secureRandomThreadLocal.get() == null) {
            this.secureRandomThreadLocal.set(new SecureRandom());
        }
        ByteBuffer seed = ByteBuffer.allocate(8);
        seed.putInt(userAgentHash);
        seed.putInt(randomSeed);
        this.secureRandomThreadLocal.get().setSeed(seed.array());
        byte[] uuid = new byte[16];
        this.secureRandomThreadLocal.get().nextBytes(uuid);

        Token token = new Token(UUID.nameUUIDFromBytes(uuid).toString(),
                DateTimeFormatter.ISO_DATE_TIME.format(
                        LocalDateTime.now()));
        tokenDao.insert(token);
        return token.getId();
    }

    @GET
    @Path("/Validation")
    @Produces(MediaType.APPLICATION_JSON)
    public Response validation(@QueryParam("token") String token) {
        if (!tokenDao.exists(token)) {
            return Response.status(Status.FORBIDDEN).build();
        }
        return Response.ok(Boolean.TRUE).build();
    }

}