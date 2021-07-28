package io.github.usalko.sy.db;

import java.util.List;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface GeometryShapeDao {

    @SqlQuery("select mnemonic from geometry_shapes order by id")
    List<String> findAll();
}
