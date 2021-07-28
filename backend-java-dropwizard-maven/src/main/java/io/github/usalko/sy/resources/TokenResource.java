package io.github.usalko.sy.resources;

import io.github.usalko.sy.db.TokenDao;
import io.github.usalko.sy.domain.Token;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.time.LocalDateTime;
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

        Token token = new Token(UUID.nameUUIDFromBytes(uuid).toString(), LocalDateTime.now());
        tokenDao.insert(token);
        return token.getId();
    }

    @GET
    @Path("/Validation")
    public Response validation(@QueryParam("token") String token) {
        if (!tokenDao.exists(token)) {
            return Response.status(Status.FORBIDDEN).build();
        }
        return Response.ok().build();
    }

}