package io.github.usalko.sy.service;

import io.github.usalko.sy.model.Mood;
import io.github.usalko.sy.repository.MoodRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class MoodServiceImpl implements MoodService {

    private final MoodRepository moodRepository;

    public MoodServiceImpl(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    @Override
    public Iterable<Mood> getAllMoods() {
        return this.moodRepository.findAll();
    }

    @Override
    public Mood create(Mood mood) {
        mood.setDateCreated(LocalDate.now());

        return this.moodRepository.save(mood);
    }

    @Override
    public void update(Mood mood) {
        this.moodRepository.save(mood);
    }
}
