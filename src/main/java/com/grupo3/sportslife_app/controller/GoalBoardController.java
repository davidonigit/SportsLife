package com.grupo3.sportslife_app.controller;

import org.springframework.web.bind.annotation.RestController;

import com.grupo3.sportslife_app.dto.GoalDTO;
import com.grupo3.sportslife_app.enums.StatusEnum;
import com.grupo3.sportslife_app.model.Goal;
import com.grupo3.sportslife_app.model.GoalBoard;
import com.grupo3.sportslife_app.service.GoalBoardService;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/goal-board")
@AllArgsConstructor
public class GoalBoardController {

    private final GoalBoardService goalBoardService;

    @GetMapping("/{id}")
    public ResponseEntity<GoalBoard> getGoalBoardById(@PathVariable Long id){
        return goalBoardService.getGoalBoardById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GoalBoard>> getAllGoalBoards(){
        return ResponseEntity.ok(goalBoardService.getAllGoalBoards());
    }

    @PostMapping("/{id}/goals")
    public ResponseEntity<GoalBoard> addGoalToBoard(@RequestBody Goal goal, @PathVariable Long id) {
        if(goal.getStatus() == null) {
            goal.setStatus(StatusEnum.TO_DO);
        }

        try{
            GoalBoard updatedGoalBoard = goalBoardService.addGoalToBoard(goal, id);
            return ResponseEntity.ok(updatedGoalBoard);
        }catch(RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/goals/{goalId}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long id,@PathVariable Long goalId){
        return goalBoardService.getGoalById(goalId)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}/goals/{goalId}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long goalId, @RequestBody GoalDTO body){
        Goal goal = goalBoardService.getGoalById(goalId)
            .orElseThrow(() -> new RuntimeException("User not found"));
        goal.setName(body.name());
        goal.setStatus(body.status());
        goalBoardService.saveGoal(goal);
        return ResponseEntity.ok(goal);
    }

    @DeleteMapping("/{id}/goals/{goalId}")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId){
        try {
            goalBoardService.deleteGoal(goalId);
            return ResponseEntity.ok().body(Map.of("message", "Meta excluída com sucesso"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao excluir meta: " + e.getMessage()));
        }
    }
}
