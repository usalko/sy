package io.github.usalko.sy.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "own_moods")
public class OwnMood extends Mood {

    @OneToMany(mappedBy = "pk.ownMood")
    @Valid
    private List<OwnMoodGeometryShape> moodGeometryShapes = new ArrayList<>();

    public OwnMood(Long id,
                   @NotNull(message = "Parent geometry form is required.") GeometryShape geometryShape) {
        this.setId(id);
        this.setGeometryShape(geometryShape);
    }

    public OwnMood() {
    }

    public List<OwnMoodGeometryShape> getMoodGeometryShapes() {
        return moodGeometryShapes;
    }

    public void setMoodGeometryShapes(List<OwnMoodGeometryShape> ownMoodGeometryShapes) {
        this.moodGeometryShapes = ownMoodGeometryShapes;
    }

}
