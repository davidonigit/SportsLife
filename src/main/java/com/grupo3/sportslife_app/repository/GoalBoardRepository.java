package com.grupo3.sportslife_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo3.sportslife_app.model.GoalBoard;

@Repository
public interface GoalBoardRepository extends JpaRepository<GoalBoard, Long> {

}