package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class TokenOwnMood {

    @EmbeddedId
    @JsonIgnore
    private TokenOwnMoodPK pk;

    @Column(nullable = false)
    private LocalDateTime moment;

    public TokenOwnMood() {
        super();
    }

    public TokenOwnMood(Token token, OwnMood ownMood, LocalDateTime moment) {
        pk = new TokenOwnMoodPK();
        pk.setToken(token);
        pk.setOwnMood(ownMood);
        this.moment = moment;
    }

    @Transient
    public Token getToken() {
        return this.pk.getToken();
    }

    public TokenOwnMoodPK getPk() {
        return pk;
    }

    public void setPk(TokenOwnMoodPK pk) {
        this.pk = pk;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenOwnMood that = (TokenOwnMood) o;
        return Objects.equals(pk, that.pk) &&
                Objects.equals(moment, that.moment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, moment);
    }
}
