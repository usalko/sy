package io.github.usalko.sy.service;

import io.github.usalko.sy.exception.ResourceNotFoundException;
import io.github.usalko.sy.model.GeometryShape;
import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.model.MoodGeometryShape;
import io.github.usalko.sy.repository.GeometryShapeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GeometryShapeServiceImpl implements GeometryShapeService {

    private final GeometryShapeRepository geometryShapeRepository;

    public GeometryShapeServiceImpl(GeometryShapeRepository GeometryShapeRepository) {
        this.geometryShapeRepository = GeometryShapeRepository;
    }

    @Override
    public Iterable<GeometryShape> getAllGeometryShapes() {
        return this.geometryShapeRepository.findAll();
    }

    @Override
    public GeometryShape getGeometryShape(long id) {
        return this.geometryShapeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GeometryShape not found"));
    }

    @Override
    public void save(GeometryShape GeometryShape) {
        this.geometryShapeRepository.save(GeometryShape);
    }

    @Override
    public <T extends MoodGeometryShape> void restoreGeometryShapes(Mood<T> mood) {
        GeometryShape shape = mood.getGeometryShape();
        this.geometryShapeRepository.findGeometryShapeByMnemonic(shape.getMnemonic()).stream().findFirst()
                .ifPresent(geometryShape -> shape.setId(geometryShape.getId()));
        mood.getMoodGeometryShapes().forEach(e -> {
            if (e != null) {
                GeometryShape moodGeometryShape = e.getGeometryShape();
                this.geometryShapeRepository.findGeometryShapeByMnemonic(moodGeometryShape.getMnemonic()).stream().findFirst()
                        .ifPresent(geometryShape -> moodGeometryShape.setId(geometryShape.getId()));
                e.setMood(mood);
            }
        });
    }
}

