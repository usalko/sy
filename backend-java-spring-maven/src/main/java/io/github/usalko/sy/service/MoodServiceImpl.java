package io.github.usalko.sy.service;

import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;
import io.github.usalko.sy.model.Token;
import io.github.usalko.sy.model.TokenOwnMood;
import io.github.usalko.sy.repository.OwnMoodRepository;
import io.github.usalko.sy.repository.SharedMoodRepository;
import io.github.usalko.sy.repository.TokenOwnMoodRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class MoodServiceImpl implements MoodService {

    private final SharedMoodRepository sharedMoodRepository;

    private final OwnMoodRepository ownMoodRepository;

    private final TokenOwnMoodRepository tokenOwnMoodRepository;

    public MoodServiceImpl(SharedMoodRepository sharedMoodRepository,
                           OwnMoodRepository ownMoodRepository,
                           TokenOwnMoodRepository tokenOwnMoodRepository) {
        this.sharedMoodRepository = sharedMoodRepository;
        this.ownMoodRepository = ownMoodRepository;
        this.tokenOwnMoodRepository = tokenOwnMoodRepository;
    }

    @Override
    public Iterable<SharedMood> getSharedMoods(int limit) {
        return this.sharedMoodRepository.findAll(Pageable.ofSize(limit));
    }

    @Override
    public Iterable<OwnMood> getOwnMoods(Token token, int limit) {
        return this.ownMoodRepository.findAll(Pageable.ofSize(limit));
    }

    @Override
    public void share(SharedMood sharedMood) {
        if (sharedMood.getCreated() == null) {
            sharedMood.setCreated(LocalDateTime.now());
        }

        this.sharedMoodRepository.save(sharedMood);
    }

    @Override
    public void keep(Token token, OwnMood ownMood) {
        if (ownMood.getCreated() == null) {
            ownMood.setCreated(LocalDateTime.now());
        }
        this.ownMoodRepository.save(ownMood);

        TokenOwnMood tokenLink = new TokenOwnMood(token, ownMood);
        this.tokenOwnMoodRepository.save(tokenLink);
    }
}
