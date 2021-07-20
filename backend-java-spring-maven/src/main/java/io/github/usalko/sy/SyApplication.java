package io.github.usalko.sy;

import io.github.usalko.sy.model.*;
import io.github.usalko.sy.service.GeometryShapeService;
import io.github.usalko.sy.service.MoodGeometryShapeService;
import io.github.usalko.sy.service.MoodService;
import io.github.usalko.sy.service.TokenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

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
