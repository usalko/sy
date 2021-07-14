package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Mood {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "pk.mood")
    @Valid
    private List<MoodGeometryShape> moodGeometryShapes = new ArrayList<>();

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<MoodGeometryShape> getMoodGeometryShapes() {
        return moodGeometryShapes;
    }

    public void setMoodGeometryShapes(List<MoodGeometryShape> moodGeometryShapes) {
        this.moodGeometryShapes = moodGeometryShapes;
    }
}
