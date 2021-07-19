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
    @Query("select o.pk.ownMood from TokenOwnMood o where o.pk.token.id = :pkTokenId")
    List<OwnMood> findAllByPkTokenIdOrderByPkOwnMoodCreatedDesc(@Param("pkTokenId") String token,
                                                                  Pageable pageable);
}