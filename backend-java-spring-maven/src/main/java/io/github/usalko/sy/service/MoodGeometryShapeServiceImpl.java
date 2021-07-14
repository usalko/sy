package io.github.usalko.sy.service;

import io.github.usalko.sy.model.MoodGeometryShape;
import io.github.usalko.sy.repository.MoodGeometryShapeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoodGeometryShapeServiceImpl implements MoodGeometryShapeService {

    private final MoodGeometryShapeRepository orderGeometryShapeRepository;

    public MoodGeometryShapeServiceImpl(MoodGeometryShapeRepository orderGeometryShapeRepository) {
        this.orderGeometryShapeRepository = orderGeometryShapeRepository;
    }

    @Override
    public MoodGeometryShape create(MoodGeometryShape orderGeometryShape) {
        return this.orderGeometryShapeRepository.save(orderGeometryShape);
    }
}
