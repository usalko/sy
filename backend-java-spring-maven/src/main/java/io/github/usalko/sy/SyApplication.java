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

import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.service.GeometryShapeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@ServletComponentScan
@SpringBootApplication
public class SyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(GeometryShapeService geometryShapeService) {
        return args -> {
            // Geometric shapes
            geometryShapeService.save(new GeometryShape(1L, "triangle"));
            geometryShapeService.save(new GeometryShape(2L, "square"));
            geometryShapeService.save(new GeometryShape(3L, "circle"));
        };
    }
}
