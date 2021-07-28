package io.github.usalko.sy.db;

import io.github.usalko.sy.domain.GeometryShape;
import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface GeometryShapeDao {

    @SqlQuery("select mnemonic from geometry_shapes order by id")
    List<String> findAllMnemonics();

    @SqlQuery("select id, mnemonic from geometry_shapes order by id")
    @RegisterBeanMapper(GeometryShape.class)
    List<GeometryShape> findAll();
}
