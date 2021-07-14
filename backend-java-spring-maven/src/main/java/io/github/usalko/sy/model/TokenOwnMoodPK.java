package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "token")
public class TokenOwnMoodPK implements Serializable {

    private static final long serialVersionUID = 7213530449320295754L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private Token token;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "own_mood_id")
    private OwnMood ownMood;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public OwnMood getOwnMood() {
        return ownMood;
    }

    public void setOwnMood(OwnMood ownMood) {
        this.ownMood = ownMood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenOwnMoodPK that = (TokenOwnMoodPK) o;
        return Objects.equals(token, that.token) &&
                Objects.equals(ownMood, that.ownMood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, ownMood);
    }
}
