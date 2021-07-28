package io.github.usalko.sy.resources;

import io.github.usalko.sy.db.MoodDao;
import io.github.usalko.sy.db.TokenDao;
import io.github.usalko.sy.domain.Mood;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MoodResource {

    private final TokenDao tokenDao;
    private final MoodDao moodDao;

    @Inject
    public MoodResource(TokenDao tokenDao, MoodDao moodDao) {
        this.tokenDao = tokenDao;
        this.moodDao = moodDao;
    }

    @GET
    @Path("/GetHistory")
    public List<Mood> getHistory(@QueryParam("token") String token) throws IllegalAccessException {
        if (!tokenDao.exists(token)) {
            throw new IllegalAccessException(
                    String.format("Token %s is not presented in the database", token));
        }
        return moodDao.findTopOwnMoodsSortedByIdDesc(token, 50);
    }

    @GET
    @Path("/GetSharedMoods")
    public List<Mood> getSharedMoods(@QueryParam("token") String token)
            throws IllegalAccessException {
        if (!tokenDao.exists(token)) {
            throw new IllegalAccessException(
                    String.format("Token %s is not presented in the database", token));
        }
        return moodDao.findTopSharedMoodsSortedByIdDesc(200);
    }

}