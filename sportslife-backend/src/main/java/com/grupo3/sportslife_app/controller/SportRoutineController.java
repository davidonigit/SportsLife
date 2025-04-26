package com.grupo3.sportslife_app.controller;



import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo3.sportslife_app.dto.DailyAvailabilityDTO;
import com.grupo3.sportslife_app.dto.SportRoutineDTO;
import com.grupo3.sportslife_app.model.SportRoutine;
import com.grupo3.sportslife_app.service.SportRoutineService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;


@Controller
@RequestMapping("/api/sport-routine")
@AllArgsConstructor
public class SportRoutineController {
    
    private final SportRoutineService sportRoutineService;

    @GetMapping("/{id}")
    public ResponseEntity<SportRoutine> getSportRoutineById(@PathVariable Long id){
        return sportRoutineService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<SportRoutine> updateSportName(@PathVariable Long id, @RequestBody SportRoutineDTO body){
        SportRoutine sportRoutine = sportRoutineService.findById(id)
            .orElseThrow(() -> new RuntimeException("Sport Routine not found"));
        
        sportRoutineService.updateSportName(id, body.name());
        return ResponseEntity.ok(sportRoutine);

    }

    public ResponseEntity<SportRoutine> updateDailyAvaliabillity(@PathVariable Long id, @RequestBody DailyAvailabilityDTO body){
        SportRoutine sportRoutine = sportRoutineService.findById(id)
            .orElseThrow(() -> new RuntimeException("Sport Routine not found"));
        
        sportRoutine.updateAvailability(body.day(), body.morning(), body.afternoon(), body.evening());
        sportRoutineService.saveSportRoutine(sportRoutine);
        return ResponseEntity.ok(sportRoutine);
    }
}
