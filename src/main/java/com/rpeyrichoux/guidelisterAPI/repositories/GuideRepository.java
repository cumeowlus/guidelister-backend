package com.rpeyrichoux.guidelisterAPI.repositories;

import com.rpeyrichoux.guidelisterAPI.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
    List<Guide> findByAuthorizedUsers_Id(Long userId);
}
