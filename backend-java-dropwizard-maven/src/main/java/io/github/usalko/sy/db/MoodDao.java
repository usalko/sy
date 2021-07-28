package io.github.usalko.sy.db;

import io.github.usalko.sy.domain.Mood;
import io.github.usalko.sy.domain.MoodGeometryShape;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

public interface MoodDao extends Transactional<MoodDao> {

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

    @SqlUpdate("insert into own_moods (created, geometry_shape_id) values (:created, :shape.id)")
    @GetGeneratedKeys()
    long saveOwnMood(@BindBean Mood mood);

    @SqlBatch(
            "insert into own_mood_geometry_shapes (index_in_list, color, own_mood_id, geometry_shape_id) "
                    + " values (:indexInList, :color, :ownMoodId, :geometryShapeId)")
    void saveOwnMoodGeometryShapes(
            @BindBean Iterable<MoodGeometryShape> moodGeometryShapes);

    @SqlUpdate("insert into token_own_moods (token_id, own_mood_id) values (:token, :own_mood_id)")
    void saveTokenOwnMood(@Bind("token") String token, @Bind("own_mood_id") long ownMoodId);
}
