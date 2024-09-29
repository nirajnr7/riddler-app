package com.riddler.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riddler.entity.Riddle;

@Repository
public interface RiddleRepository extends JpaRepository<Riddle, Integer> {
}
