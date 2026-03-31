package com.rpeyrichoux.guidelisterAPI.repositories;

import com.rpeyrichoux.guidelisterAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query(value = "select distinct\n" +
            "  u.*\n" +
            "from\n" +
            "  users u,\n" +
            "  guide_user gu\n" +
            "where\n" +
            "  u.id = gu.user_id\n" +
            "  and gu.guide_id = ?1", nativeQuery = true)
    List<User> findDistinctByGuide(Long guideId);
}
