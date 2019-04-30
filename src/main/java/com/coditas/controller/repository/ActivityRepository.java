package com.coditas.controller.repository;

import com.coditas.controller.entity.Activity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Harshal Patil
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByUserEmail(String email, Pageable pageable);
}