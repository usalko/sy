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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user")
public class UserOwnMoodPK implements Serializable {

    private static final long serialVersionUID = 7213530449320295754L;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "own_mood_id")
    private OwnMood ownMood;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        UserOwnMoodPK that = (UserOwnMoodPK) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(ownMood, that.ownMood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, ownMood);
    }
}
