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
    public void create(OwnMoodGeometryShape ownMoodGeometryShape) {
        this.ownMoodGeometryShapeRepository.save(ownMoodGeometryShape);
    }

    @Override
    public void create(SharedMoodGeometryShape sharedMoodGeometryShape) {
        this.sharedMoodGeometryShapeRepository.save(sharedMoodGeometryShape);
    }
}
