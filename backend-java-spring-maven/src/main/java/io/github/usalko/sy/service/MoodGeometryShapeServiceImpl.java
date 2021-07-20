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
