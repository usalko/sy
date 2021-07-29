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

package io.github.usalko.sy.db;

import io.github.usalko.sy.domain.Mood;
import io.github.usalko.sy.domain.MoodGeometryShape;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transactional;

public interface MoodDao extends Transactional<MoodDao> {

    @SqlQuery(
            "select om.id as id, om.created, om.geometry_shape_id as geometryShapeId from token_own_moods tom "
                    + " inner join own_moods om on (tom.own_mood_id = om.id) "
                    + "where tom.token_id = :token "
                    + "order by om.id desc "
                    + "limit :limit")
    @RegisterBeanMapper(Mood.class)
    List<Mood> findTopOwnMoodsSortedByIdDesc(@Bind("token") String token,
            @Bind("limit") int limit);

    @SqlQuery(
            "select index_in_list as indexInList, color, geometry_shape_id as geometryShapeId, own_mood_id as moodId from own_mood_geometry_shapes "
                    + "where own_mood_id in (<mood_ids>) ")
    @RegisterBeanMapper(MoodGeometryShape.class)
    List<MoodGeometryShape> findOwnMoodGeometryShapesByMoodIds(
            @BindList("mood_ids") Iterable<Long> moodIds);

    @SqlQuery(
            "select sm.id as id, sm.created, sm.geometry_shape_id as geometryShapeId from shared_moods sm "
                    + "order by sm.id desc "
                    + "limit :limit")
    @RegisterBeanMapper(Mood.class)
    List<Mood> findTopSharedMoodsSortedByIdDesc(@Bind("limit") int limit);

    @SqlQuery(
            "select index_in_list as indexInList, color, geometry_shape_id as geometryShapeId, shared_mood_id as moodId from shared_mood_geometry_shapes "
                    + "where shared_mood_id in (<mood_ids>) ")
    @RegisterBeanMapper(MoodGeometryShape.class)
    List<MoodGeometryShape> findSharedMoodGeometryShapesByMoodIds(
            @BindList("mood_ids") Iterable<Long> moodIds);

    @SqlUpdate("insert into shared_moods (created, geometry_shape_id) values (:created, :shape_id)")
    @GetGeneratedKeys()
    long saveSharedMood(@Bind("created") String created, @Bind("shape_id") Long shapeId);

    @SqlUpdate("insert into own_moods (created, geometry_shape_id) values (:created, :shape_id)")
    @GetGeneratedKeys()
    long saveOwnMood(@Bind("created") String created, @Bind("shape_id") Long shapeId);

    @SqlBatch(
            "insert into own_mood_geometry_shapes (index_in_list, color, own_mood_id, geometry_shape_id) "
                    + " values (:indexInList, :color, :moodId, :geometryShapeId)")
    void saveOwnMoodGeometryShapes(
            @BindBean Iterable<MoodGeometryShape> moodGeometryShapes);

    @SqlBatch(
            "insert into shared_mood_geometry_shapes (index_in_list, color, shared_mood_id, geometry_shape_id) "
                    + " values (:indexInList, :color, :moodId, :geometryShapeId)")
    void saveSharedMoodGeometryShapes(@BindBean Iterable<MoodGeometryShape> valuableContent);


    @SqlUpdate("insert into token_own_moods (token_id, own_mood_id) values (:token, :own_mood_id)")
    void saveTokenOwnMood(@Bind("token") String token, @Bind("own_mood_id") long ownMoodId);

}
