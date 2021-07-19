package io.github.usalko.sy.controller;

import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.service.GeometryShapeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Health check api
 */
@RestController
@RequestMapping("/api/Geometry")
public class HealthCheckController {

    private final GeometryShapeService GeometryShapeService;

    public HealthCheckController(io.github.usalko.sy.service.GeometryShapeService GeometryShapeService) {
        this.GeometryShapeService = GeometryShapeService;
    }

    @GetMapping(value = {""})
    public @NotNull Iterable<String> getGeometryShapes() {
        return StreamSupport.stream(GeometryShapeService.getAllGeometryShapes().spliterator(), false)
                .map(GeometryShape::getMnemonic).collect(Collectors.toList());
    }
}
