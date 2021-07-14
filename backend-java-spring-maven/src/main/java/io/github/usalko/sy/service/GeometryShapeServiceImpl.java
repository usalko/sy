package io.github.usalko.sy.service;

import io.github.usalko.sy.exception.ResourceNotFoundException;
import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.repository.GeometryShapeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GeometryShapeServiceImpl implements GeometryShapeService {

    private final GeometryShapeRepository GeometryShapeRepository;

    public GeometryShapeServiceImpl(GeometryShapeRepository GeometryShapeRepository) {
        this.GeometryShapeRepository = GeometryShapeRepository;
    }

    @Override
    public Iterable<GeometryShape> getAllGeometryShapes() {
        return GeometryShapeRepository.findAll();
    }

    @Override
    public GeometryShape getGeometryShape(long id) {
        return GeometryShapeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeometryShape not found"));
    }

    @Override
    public GeometryShape save(GeometryShape GeometryShape) {
        return GeometryShapeRepository.save(GeometryShape);
    }
}

