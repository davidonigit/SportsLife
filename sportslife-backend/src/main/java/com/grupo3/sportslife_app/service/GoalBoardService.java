package com.grupo3.sportslife_app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo3.sportslife_app.model.Goal;
import com.grupo3.sportslife_app.model.GoalBoard;
import com.grupo3.sportslife_app.repository.GoalBoardRepository;
import com.grupo3.sportslife_app.repository.GoalRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GoalBoardService {
    
    private final GoalBoardRepository goalBoardRepository;
    private final GoalRepository goalRepository;

    public List<GoalBoard> getAllGoalBoards(){
        return goalBoardRepository.findAll();
    }

    public Optional<GoalBoard> getGoalBoardById(Long id){
        return goalBoardRepository.findById(id);
    }

    public Optional<GoalBoard> findByUserId(Long userId) {
        return goalBoardRepository.findByUserId(userId);
    }

    public Optional<Goal> getGoalById(Long goalId){
        return goalRepository.findById(goalId);
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

    public Goal saveGoal(Goal goal){
        return goalRepository.save(goal);
    }

    //public void deleteGoalBoard(Long id){
    //    goalBoardRepository.deleteById(id);
    //}
    
    @Transactional
    public void deleteGoal(Long goalId){
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new EntityNotFoundException("Meta não encontrada com ID: " + goalId));
        
        GoalBoard goalBoard = goal.getGoalBoard();
        if(goalBoard != null){
            goalBoard.getGoals().remove(goal);
        }

        goalRepository.delete(goal);

    }
}
