package com.grupo3.sportslife_app.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo3.sportslife_app.model.GoalBoard;
import com.grupo3.sportslife_app.repository.GoalBoardRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GoalBoardService {
    
    private final GoalBoardRepository goalBoardRepository;

    public Optional<GoalBoard> findGoalBoardById(Long id){
        return goalBoardRepository.findById(id);
    }

    public GoalBoard saveGoalBoard(GoalBoard goalBoard){
        return goalBoardRepository.save(goalBoard);
    }

    public void deleteGoalBoard(Long id){
        goalBoardRepository.deleteById(id);
    }
}
