package com.rpeyrichoux.guidelisterAPI.repositories;

import com.rpeyrichoux.guidelisterAPI.model.Guide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
}
