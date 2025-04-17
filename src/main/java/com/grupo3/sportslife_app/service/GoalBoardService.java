package com.grupo3.sportslife_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo3.sportslife_app.model.Goal;
import com.grupo3.sportslife_app.model.GoalBoard;
import com.grupo3.sportslife_app.repository.GoalBoardRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GoalBoardService {
    
    private final GoalBoardRepository goalBoardRepository;

    public List<GoalBoard> getAllGoalBoards(){
        return goalBoardRepository.findAll();
    }

    public Optional<GoalBoard> getGoalBoardById(Long id){
        return goalBoardRepository.findById(id);
    }

    public GoalBoard addGoalToBoard(Goal goal, Long boardId){
        GoalBoard goalBoard = goalBoardRepository.findById(boardId)
            .orElseThrow(() -> new RuntimeException("GoalBoard not found with id: " + boardId));
        
        goal.setGoalBoard(goalBoard);
        goalBoard.addGoal(goal);

        return saveGoalBoard(goalBoard);
    }

    public GoalBoard saveGoalBoard(GoalBoard goalBoard){
        return goalBoardRepository.save(goalBoard);
    }

    public void deleteGoalBoard(Long id){
        goalBoardRepository.deleteById(id);
    }
}
