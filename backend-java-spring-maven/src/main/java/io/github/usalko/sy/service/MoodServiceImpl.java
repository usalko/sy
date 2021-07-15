package io.github.usalko.sy.service;

import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.SharedMood;
import io.github.usalko.sy.repository.OwnMoodRepository;
import io.github.usalko.sy.repository.SharedMoodRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MoodServiceImpl implements MoodService {

    private final SharedMoodRepository sharedMoodRepository;

    private final OwnMoodRepository ownMoodRepository;

    public MoodServiceImpl(SharedMoodRepository sharedMoodRepository, OwnMoodRepository ownMoodRepository) {
        this.sharedMoodRepository = sharedMoodRepository;
        this.ownMoodRepository = ownMoodRepository;
    }

    @Override
    public Iterable<? extends Mood> getSharedMoods(int limit) {
        return this.sharedMoodRepository.findAll();
    }

    @Override
    public Iterable<? extends Mood> getOwnMoods(String token, int limit) {
        return this.ownMoodRepository.findAll(Pageable.ofSize(limit));
    }

    @Override
    public Mood share(String token, Mood mood) {
        mood.setDateCreated(LocalDate.now());
        // TODO: create reference to token

        return this.sharedMoodRepository.save((SharedMood) mood);
    }

    @Override
    public void keep(String token, Mood mood) {
        // TODO: create reference to token

        this.ownMoodRepository.save((OwnMood) mood);
    }
}
