package io.github.usalko.sy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OwnMood extends Mood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Parent geometry form is required.")
    @Basic(optional = false)
    private GeometryShape parentForm;

    @NotNull(message = "Content is required.")
    @Basic(optional = false)
    private String content;

    public OwnMood(Long id,
                   @NotNull(message = "Parent geometry form is required.") GeometryShape parentForm,
                   @NotNull(message = "Content is required.") String content) {
        this.id = id;
        this.parentForm = parentForm;
        this.content = content;
    }

    public OwnMood() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GeometryShape getParentForm() {
        return parentForm;
    }

    public void setParentForm(GeometryShape parentForm) {
        this.parentForm = parentForm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
