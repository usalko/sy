package io.github.usalko.sy;

import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.service.GeometryShapeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(GeometryShapeService geometryShapeService) {
        return args -> {
            geometryShapeService.save(new GeometryShape(1L, "triangle"));
            geometryShapeService.save(new GeometryShape(2L, "square"));
            geometryShapeService.save(new GeometryShape(3L, "circle"));
        };
    }
}
