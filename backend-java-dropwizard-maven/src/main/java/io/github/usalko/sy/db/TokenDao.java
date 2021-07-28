package io.github.usalko.sy.db;

import io.github.usalko.sy.domain.Token;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface TokenDao {

    @SqlQuery("select count(*) > 0 from tokens where id = :id")
    boolean exists(@Bind("id") String id);

    @SqlUpdate("insert into tokens (id, created) values (:id, :created)")
    void insert(@BindBean Token token);

}
