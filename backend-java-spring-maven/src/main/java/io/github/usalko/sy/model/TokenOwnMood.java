package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
public class TokenOwnMood {

    @EmbeddedId
    @JsonIgnore
    private TokenOwnMoodPK pk;

    public TokenOwnMood() {
        super();
    }

    public TokenOwnMood(Token token, OwnMood ownMood) {
        pk = new TokenOwnMoodPK();
        pk.setToken(token);
        pk.setOwnMood(ownMood);
    }

    @Transient
    public Token getToken() {
        return this.pk.getToken();
    }

    @Transient
    public OwnMood getOwnMood() {
        return this.pk.getOwnMood();
    }

    public TokenOwnMoodPK getPk() {
        return pk;
    }

    public void setPk(TokenOwnMoodPK pk) {
        this.pk = pk;
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
        return Objects.equals(pk, that.pk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
