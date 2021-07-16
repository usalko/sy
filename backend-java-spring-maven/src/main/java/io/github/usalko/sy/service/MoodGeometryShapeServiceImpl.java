package io.github.usalko.sy.service;

import io.github.usalko.sy.model.OwnMoodGeometryShape;
import io.github.usalko.sy.model.SharedMoodGeometryShape;
import io.github.usalko.sy.repository.OwnMoodGeometryShapeRepository;
import io.github.usalko.sy.repository.SharedMoodGeometryShapeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MoodGeometryShapeServiceImpl implements MoodGeometryShapeService {

    private final OwnMoodGeometryShapeRepository ownMoodGeometryShapeRepository;
    private final SharedMoodGeometryShapeRepository sharedMoodGeometryShapeRepository;

    public MoodGeometryShapeServiceImpl(OwnMoodGeometryShapeRepository ownMoodGeometryShapeRepository,
                                        SharedMoodGeometryShapeRepository sharedMoodGeometryShapeRepository) {
        this.ownMoodGeometryShapeRepository = ownMoodGeometryShapeRepository;
        this.sharedMoodGeometryShapeRepository = sharedMoodGeometryShapeRepository;
    }

    @Override
    public OwnMoodGeometryShape create(OwnMoodGeometryShape ownMoodGeometryShape) {
        return this.ownMoodGeometryShapeRepository.save(ownMoodGeometryShape);
    }

    @Override
    public SharedMoodGeometryShape create(SharedMoodGeometryShape sharedMoodGeometryShape) {
        return this.sharedMoodGeometryShapeRepository.save(sharedMoodGeometryShape);
    }
}
