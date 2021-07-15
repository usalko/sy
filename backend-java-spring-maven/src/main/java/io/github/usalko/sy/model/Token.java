package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tokens")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="tokenOwnMoods")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "pk.token")
    @Valid
    private List<TokenOwnMood> tokenOwnMoods = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public List<TokenOwnMood> getTokenOwnMoods() {
        return tokenOwnMoods;
    }

    public void setTokenOwnMoods(List<TokenOwnMood> tokenOwnMoods) {
        this.tokenOwnMoods = tokenOwnMoods;
    }
}
