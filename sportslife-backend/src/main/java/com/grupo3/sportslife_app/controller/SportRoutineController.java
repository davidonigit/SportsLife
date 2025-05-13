package com.grupo3.sportslife_app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.grupo3.sportslife_app.dto.SportRoutineDTO;
import com.grupo3.sportslife_app.model.SportRoutine;
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
    
    /* @GetMapping("/{id}")
    public ResponseEntity<SportRoutine> getSportRoutineById(@PathVariable Long id){
        return sportRoutineService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    } */

    @PutMapping
    public ResponseEntity<SportRoutine> updateSportRoutine(@RequestBody SportRoutineDTO body){
        Long userId = securityUtils.getCurrentUserId();
        SportRoutine sportRoutine = sportRoutineService.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Sport Routine not found"));
        
        sportRoutineService.updateSportName(sportRoutine.getId(), body.name());
        
        sportRoutine.updateAvailability(body.monday(), body.mondayMorning(), body.mondayAfternoon(), body.mondayEvening());
        sportRoutine.updateAvailability(body.tuesday(), body.tuesdayMorning(), body.tuesdayAfternoon(), body.tuesdayEvening());
        sportRoutine.updateAvailability(body.wednesday(), body.wednesdayMorning(), body.wednesdayAfternoon(), body.wednesdayEvening());
        sportRoutine.updateAvailability(body.thursday(), body.thursdayMorning(), body.thursdayAfternoon(), body.thursdayEvening());
        sportRoutine.updateAvailability(body.friday(), body.fridayMorning(), body.fridayAfternoon(), body.fridayEvening());
        sportRoutine.updateAvailability(body.saturday(), body.saturdayMorning(), body.saturdayAfternoon(), body.saturdayEvening());
        sportRoutine.updateAvailability(body.sunday(), body.sundayMorning(), body.sundayAfternoon(), body.sundayEvening());
        sportRoutineService.saveSportRoutine(sportRoutine);
        return ResponseEntity.ok(sportRoutine);
    }
}
