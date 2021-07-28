package io.github.usalko.sy.db;

import io.github.usalko.sy.domain.Mood;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface MoodDao {

    @SqlQuery(
            "select om.id as id, shape.id as shape_id, shape.mnemonic as shape_mnemonic from token_own_moods tom "
                    + " inner join own_moods om on (tom.own_mood_id = om.id) "
                    + " inner join own_mood_geometry_shapes omgs on (om.id = omgs.own_mood_id) "
                    + " inner join geometry_shapes shape on (omgs.geometry_shape_id = shape.id) "
                    + "where tom.token_id = :token "
                    + "order by om.id desc "
                    + "limit :limit")
    @RegisterBeanMapper(Mood.class)
    List<Mood> findTopOwnMoodsSortedByIdDesc(@Bind("token") String token,
            @Bind("limit") int limit);

    @SqlQuery(
            "select sm.id as id, shape.id as shape_id, shape.mnemonic as shape_mnemonic from shared_moods sm "
                    + " inner join shared_mood_geometry_shapes smgs on (sm.id = smgs.shared_mood_id) "
                    + " inner join geometry_shapes shape on (smgs.geometry_shape_id = shape.id) "
                    + "order by sm.id desc "
                    + "limit :limit")
    @RegisterBeanMapper(Mood.class)
    List<Mood> findTopSharedMoodsSortedByIdDesc(@Bind("limit") int limit);

}
