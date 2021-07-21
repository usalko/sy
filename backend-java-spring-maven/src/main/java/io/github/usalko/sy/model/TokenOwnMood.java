// Sy (Share your mood with anyone)
// Copyright (C) July 2021 Ivan Usalko <ivict@rambler.ru>
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
package io.github.usalko.sy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Objects;

@Entity
@Table(name = "token_own_moods")
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
