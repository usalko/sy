package io.github.usalko.sy.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class OwnMood {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Parent geometry form is required.")
    @Basic(optional = false)
    private GeometryForm parentForm;

    @NotNull(message = "Content is required.")
    @Basic(optional = false)
    private String content;

    public OwnMood(Long id,
                   @NotNull(message = "Parent geometry form is required.") GeometryForm parentForm,
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

    public GeometryForm getParentForm() {
        return parentForm;
    }

    public void setParentForm(GeometryForm parentForm) {
        this.parentForm = parentForm;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
