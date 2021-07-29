package io.github.usalko.sy;

import static org.assertj.core.api.Assertions.assertThat;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.github.usalko.sy.db.GeometryShapeDao;
import io.github.usalko.sy.domain.GeometryShape;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class GeometryShapeDaoTest {

    private GeometryShapeDao geometryShapeDao;

    @BeforeEach
    void setUp() throws Throwable {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        final Jdbi jdbi = Jdbi.create(() -> connection);
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));
        Liquibase liquibase = new liquibase.Liquibase("migrations.xml",
                new ClassLoaderResourceAccessor(), database);
        liquibase.update(new Contexts(), new LabelExpression());

        jdbi.installPlugin(new SqlObjectPlugin());
        geometryShapeDao = jdbi.onDemand(GeometryShapeDao.class);

    }

    @Test
    void findAll() {
        final List<GeometryShape> geometryShapes = geometryShapeDao.findAll();
        assertThat(geometryShapes).extracting("mnemonic")
                .containsOnly("triangle", "square", "circle");
    }

}