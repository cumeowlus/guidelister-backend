package com.rpeyrichoux.guidelisterAPI.repositories;

import com.rpeyrichoux.guidelisterAPI.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
