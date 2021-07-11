package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class UserOwnMood {

    @EmbeddedId
    @JsonIgnore
    private UserOwnMoodPK pk;

    @Column(nullable = false)
    private LocalDateTime moment;

    public UserOwnMood() {
        super();
    }

    public UserOwnMood(User user, OwnMood ownMood, LocalDateTime moment) {
        pk = new UserOwnMoodPK();
        pk.setUser(user);
        pk.setOwnMood(ownMood);
        this.moment = moment;
    }

    @Transient
    public User getUser() {
        return this.pk.getUser();
    }

    public UserOwnMoodPK getPk() {
        return pk;
    }

    public void setPk(UserOwnMoodPK pk) {
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
        UserOwnMood that = (UserOwnMood) o;
        return Objects.equals(pk, that.pk) &&
                Objects.equals(moment, that.moment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, moment);
    }
}
