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
package io.github.usalko.sy.repository;

import io.github.usalko.sy.model.OwnMood;
import io.github.usalko.sy.model.TokenOwnMood;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//@see the different approaches in https://stackoverflow.com/questions/9314078/setmaxresults-for-spring-data-jpa-annotation
public interface TokenOwnMoodRepository extends JpaRepository<TokenOwnMood, Long> {
    @Query("select o.pk.ownMood from TokenOwnMood o where o.pk.token.id = :pkTokenId order by o.pk.ownMood desc")
    List<OwnMood> findAllByPkTokenIdOrderByPkOwnMoodCreatedDesc(@Param("pkTokenId") String token,
                                                                Pageable pageable);
}