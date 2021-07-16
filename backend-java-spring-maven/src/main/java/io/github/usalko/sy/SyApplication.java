package io.github.usalko.sy;

import io.github.usalko.sy.model.*;
import io.github.usalko.sy.service.GeometryShapeService;
import io.github.usalko.sy.service.MoodGeometryShapeService;
import io.github.usalko.sy.service.MoodService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Collections;

@SpringBootApplication
public class SyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SyApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(GeometryShapeService geometryShapeService,
                             MoodService moodService,
                             MoodGeometryShapeService moodGeometryShapeService) {
        return args -> {
            // Geometric shapes
            geometryShapeService.save(new GeometryShape(1L, "triangle"));
            geometryShapeService.save(new GeometryShape(2L, "square"));
            geometryShapeService.save(new GeometryShape(3L, "circle"));

            // Own mood sample
            OwnMood ownMood = new OwnMood();
            ownMood.setGeometryShape(new GeometryShape(1L, "triangle"));
            OwnMoodGeometryShape ownMoodGeometryShape = new OwnMoodGeometryShape();
            ownMoodGeometryShape.setColor(0);
            OwnMoodGeometryShapePK ownMoodGeometryShapePK = new OwnMoodGeometryShapePK();
            ownMoodGeometryShapePK.setGeometryShape(new GeometryShape(1L, "triangle"));
            ownMoodGeometryShapePK.setOwnMood(ownMood);
            ownMoodGeometryShape.setPk(ownMoodGeometryShapePK);
            ownMood.setMoodGeometryShapes(Collections.singletonList(ownMoodGeometryShape));
            moodService.keep("1", ownMood);
            moodGeometryShapeService.create(ownMoodGeometryShape);

            // Shared mood sample
            SharedMood sharedMood = new SharedMood();
            sharedMood.setGeometryShape(new GeometryShape(1L, "circle"));
            SharedMoodGeometryShape sharedMoodGeometryShape = new SharedMoodGeometryShape();
            sharedMoodGeometryShape.setColor(0);
            SharedMoodGeometryShapePK sharedMoodGeometryShapePK = new SharedMoodGeometryShapePK();
            sharedMoodGeometryShapePK.setGeometryShape(new GeometryShape(1L, "circle"));
            sharedMoodGeometryShapePK.setSharedMood(sharedMood);
            sharedMoodGeometryShape.setPk(sharedMoodGeometryShapePK);
            sharedMood.setMoodGeometryShapes(Collections.singletonList(sharedMoodGeometryShape));
            moodService.share("1", sharedMood);
            moodGeometryShapeService.create(sharedMoodGeometryShape);
        };
    }
}
