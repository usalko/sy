package io.github.usalko.sy.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shared_moods")
public class SharedMood extends Mood {

    @OneToMany(mappedBy = "pk.sharedMood")
    @Valid
    private List<SharedMoodGeometryShape> moodGeometryShapes = new ArrayList<>();

    public SharedMood(Long id,
                      @NotNull(message = "Parent geometry form is required.") GeometryShape geometryShape) {
        this.setId(id);
        this.setGeometryShape(geometryShape);
    }

    public SharedMood() {
    }

    @Override
    public List<SharedMoodGeometryShape> getMoodGeometryShapes() {
        return moodGeometryShapes;
    }

    public void setMoodGeometryShapes(List<SharedMoodGeometryShape> sharedMoodGeometryShapes) {
        this.moodGeometryShapes = sharedMoodGeometryShapes;
    }

}
