//// Sy (Share your mood with anyone)
//// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
////
//// This program is free software: you can redistribute it and/or modify
//// it under the terms of the GNU General Public License as published by
//// the Free Software Foundation, either version 3 of the License, or
//// (at your option) any later version.
////
//// This program is distributed in the hope that it will be useful,
//// but WITHOUT ANY WARRANTY; without even the implied warranty of
//// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//// GNU General Public License for more details.
////
//// You should have received a copy of the GNU General Public License
//// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//package io.github.usalko.sy.service;
//
//import io.github.usalko.sy.model.OwnMood;
//import io.github.usalko.sy.model.SharedMood;
//import io.github.usalko.sy.model.Token;
//import io.github.usalko.sy.model.TokenOwnMood;
//import io.github.usalko.sy.repository.OwnMoodRepository;
//import io.github.usalko.sy.repository.SharedMoodRepository;
//import io.github.usalko.sy.repository.TokenOwnMoodRepository;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Service
//@Transactional
//public class MoodServiceImpl implements MoodService {
//
//    private final SharedMoodRepository sharedMoodRepository;
//
//    private final OwnMoodRepository ownMoodRepository;
//
//    private final TokenOwnMoodRepository tokenOwnMoodRepository;
//
//    public MoodServiceImpl(SharedMoodRepository sharedMoodRepository,
//                           OwnMoodRepository ownMoodRepository,
//                           TokenOwnMoodRepository tokenOwnMoodRepository) {
//        this.sharedMoodRepository = sharedMoodRepository;
//        this.ownMoodRepository = ownMoodRepository;
//        this.tokenOwnMoodRepository = tokenOwnMoodRepository;
//    }
//
//    @Override
//    public Iterable<SharedMood> getSharedMoods(int limit) {
//        return this.sharedMoodRepository.findByOrderByIdDesc(Pageable.ofSize(limit));
//    }
//
//    @Override
//    public Iterable<OwnMood> getOwnMoods(Token token, int limit) {
//        return this.tokenOwnMoodRepository
//                .findAllByPkTokenIdOrderByPkOwnMoodCreatedDesc(token.getId(), Pageable.ofSize(50));
//    }
//
//    @Override
//    public void share(SharedMood sharedMood) {
//        if (sharedMood.getCreated() == null) {
//            sharedMood.setCreated(LocalDateTime.now());
//        }
//
//        this.sharedMoodRepository.save(sharedMood);
//    }
//
//    @Override
//    public void keep(Token token, OwnMood ownMood) {
//        if (ownMood.getCreated() == null) {
//            ownMood.setCreated(LocalDateTime.now());
//        }
//        this.ownMoodRepository.save(ownMood);
//
//        TokenOwnMood tokenLink = new TokenOwnMood(token, ownMood);
//        this.tokenOwnMoodRepository.save(tokenLink);
//    }
//}
