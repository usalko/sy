package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tokens")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "tokenOwnMoods")
public class Token {

    @Id
    private String id;

    private LocalDateTime created;

    @OneToMany(mappedBy = "pk.token", fetch = FetchType.LAZY)
    @Valid
    private List<TokenOwnMood> tokenOwnMoods = new ArrayList<>();

    public Token(String id) {
        this.id = id;
        this.created = LocalDateTime.now();
    }

    public Token() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<TokenOwnMood> getTokenOwnMoods() {
        return tokenOwnMoods;
    }

    public void setTokenOwnMoods(List<TokenOwnMood> tokenOwnMoods) {
        this.tokenOwnMoods = tokenOwnMoods;
    }
}
