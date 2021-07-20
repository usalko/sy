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
