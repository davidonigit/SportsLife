package com.grupo3.sportslife_app.controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo3.sportslife_app.dto.SportRoutineDTO;
import com.grupo3.sportslife_app.model.SportRoutine;
import com.grupo3.sportslife_app.model.SportRoutineHistory;
import com.grupo3.sportslife_app.security.SecurityUtils;
import com.grupo3.sportslife_app.service.SportRoutineService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;



@Controller
@RequestMapping("/api/sport-routine")
@AllArgsConstructor
public class SportRoutineController {
    
    private final SportRoutineService sportRoutineService;
    private final SecurityUtils securityUtils;
    
    @GetMapping
    public ResponseEntity<SportRoutine> getMyRoutine() {
        Long userId = securityUtils.getCurrentUserId();
        return sportRoutineService.findByUserId(userId)
                .map(routine -> ResponseEntity.ok(routine))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping("/generate")
    public ResponseEntity<String> generateSportRoutine() {
        Long userId = securityUtils.getCurrentUserId();
        SportRoutine sportRoutine = sportRoutineService.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Sport Routine not found"));

        String routine = sportRoutineService.generateSportRoutine(sportRoutine.getId());

        return ResponseEntity.ok(routine);
    }
    

    @PutMapping
    public ResponseEntity<SportRoutine> updateSportRoutine(@RequestBody SportRoutineDTO body){

        Long userId = securityUtils.getCurrentUserId();
        SportRoutine sportRoutine = sportRoutineService.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Sport Routine not found"));
        
        sportRoutineService.updateSportName(sportRoutine.getId(), body.sport());

        for (var dailyAvailability : body.weeklyAvailability()) {
            sportRoutine.updateAvailability(
                dailyAvailability.dayOfWeek(),
                dailyAvailability.morningAvailable(),
                dailyAvailability.afternoonAvailable(),
                dailyAvailability.eveningAvailable()
            );
        }
        
        sportRoutineService.saveSportRoutine(sportRoutine);
        return ResponseEntity.ok(sportRoutine);
    }

    @GetMapping("/history")
    public ResponseEntity<List<SportRoutineHistory>> getSportRoutineHistory() {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(sportRoutineService.getSportRoutineHistory(userId));
    }
    
}
