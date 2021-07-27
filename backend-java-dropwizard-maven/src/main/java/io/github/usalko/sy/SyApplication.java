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
package io.github.usalko.sy;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.github.usalko.sy.health.NoopHealthCheck;
import io.github.usalko.sy.resources.GeometryResource;

public class SyApplication extends Application<SyConfiguration> {

    public static void main(String[] args) throws Exception {
        new SyApplication().run(args);
    }

    @Override
    public String getName() {
        return "Sy";
    }

    @Override
    public void initialize(Bootstrap<SyConfiguration> bootstrap) {
        // Enable variable substitution with environment variables
        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(
                        bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(new MigrationsBundle<SyConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(SyConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(SyConfiguration configuration, Environment environment) {
        //final JdbiFactory factory = new JdbiFactory();
        //final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(),
        //        "postgresql");
        //jdbi.installPlugin(new SqlObjectPlugin());
        //environment.jersey().register(new UserResource(jdbi));
        environment.healthChecks().register("noop-health-check", new NoopHealthCheck());
        environment.jersey().register(new GeometryResource());
    }

//    @Bean
//    CommandLineRunner runner(GeometryShapeService geometryShapeService) {
//        return args -> {
//            // Geometric shapes
//            geometryShapeService.save(new GeometryShape(1L, "triangle"));
//            geometryShapeService.save(new GeometryShape(2L, "square"));
//            geometryShapeService.save(new GeometryShape(3L, "circle"));
//        };
//    }
}
