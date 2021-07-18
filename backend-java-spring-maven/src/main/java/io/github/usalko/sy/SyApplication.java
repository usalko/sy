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
    CommandLineRunner runner(GeometryShapeService geometryShapeService,
                             MoodService moodService,
                             MoodGeometryShapeService moodGeometryShapeService,
                             TokenService tokenService) {
        return args -> {
            // Geometric shapes
            geometryShapeService.save(new GeometryShape(1L, "triangle"));
            geometryShapeService.save(new GeometryShape(2L, "square"));
            geometryShapeService.save(new GeometryShape(3L, "circle"));

            Token token = tokenService.generate(0, 0);

            // Own mood sample
            OwnMood ownMood = new OwnMood();
            ownMood.setGeometryShape(new GeometryShape(1L, "triangle"));
            OwnMoodGeometryShape ownMoodGeometryShape = new OwnMoodGeometryShape(ownMood,
                    new GeometryShape(1L, "triangle"), 0, 0);
            ownMood.setMoodGeometryShapes(Collections.singletonList(ownMoodGeometryShape));
            moodService.keep(token, ownMood);
            moodGeometryShapeService.create(ownMoodGeometryShape);

            // Shared mood sample
            SharedMood sharedMood = new SharedMood();
            sharedMood.setGeometryShape(new GeometryShape(1L, "circle"));
            SharedMoodGeometryShape sharedMoodGeometryShape = new SharedMoodGeometryShape(sharedMood,
                    new GeometryShape(1L, "circle"), 0, 0);
            sharedMood.setMoodGeometryShapes(Collections.singletonList(sharedMoodGeometryShape));
            moodService.share(sharedMood);
            moodGeometryShapeService.create(sharedMoodGeometryShape);
        };
    }
}
