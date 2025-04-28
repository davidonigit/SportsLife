package com.grupo3.sportslife_app.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo3.sportslife_app.dto.DailyAvailabilityDTO;
import com.grupo3.sportslife_app.enums.DayOfWeekEnum;
import com.grupo3.sportslife_app.model.DailyAvailability;
import com.grupo3.sportslife_app.model.SportRoutine;
import com.grupo3.sportslife_app.repository.DailyAvailabilityRepository;
import com.grupo3.sportslife_app.repository.SportRoutineRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class SportRoutineService {
    
    private final SportRoutineRepository sportRoutineRepository;
    private final DailyAvailabilityRepository dailyAvailabilityRepository;
    

    public Optional<SportRoutine> findById(Long id) {
        return sportRoutineRepository.findById(id);
    }
    

    public Optional<SportRoutine> findByUserId(Long userId) {
        return sportRoutineRepository.findByUserId(userId);
    }

    public SportRoutine updateSportName(Long routineId, String sportName) {
        SportRoutine sportRoutine = sportRoutineRepository.findById(routineId)
            .orElseThrow(() -> new EntityNotFoundException("Rotina esportiva não encontrada com ID: " + routineId));
        
        sportRoutine.setSportName(sportName);
        return sportRoutineRepository.save(sportRoutine);
    }


    public SportRoutine initializeWeeklyAvailability(Long routineId) {
        SportRoutine sportRoutine = sportRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Rotina esportiva não encontrada com ID: " + routineId));
        
        sportRoutine.getWeeklyAvailability().clear();
        
        for (DayOfWeekEnum day : DayOfWeekEnum.values()) {
            DailyAvailability availability = new DailyAvailability();
            availability.setDayOfWeek(day);
            availability.setMorningAvailable(false);
            availability.setAfternoonAvailable(false);
            availability.setEveningAvailable(false);
            availability.setSportRoutine(sportRoutine);
            
            sportRoutine.getWeeklyAvailability().add(availability);
            dailyAvailabilityRepository.save(availability);
        }

        return sportRoutineRepository.save(sportRoutine);
    }
    

    public SportRoutine updateDailyAvailability(
            Long routineId, 
            DayOfWeekEnum day, 
            boolean morning, 
            boolean afternoon, 
            boolean evening) {
        
        SportRoutine sportRoutine = sportRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Rotina esportiva não encontrada com ID: " + routineId));
        
        sportRoutine.updateAvailability(day, morning, afternoon, evening);
        return sportRoutineRepository.save(sportRoutine);
    }
    

    public SportRoutine updateMultipleDaysAvailability(
            Long routineId, 
            Map<DayOfWeekEnum, DailyAvailabilityDTO> availabilityMap) {
        
        SportRoutine sportRoutine = sportRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Rotina esportiva não encontrada com ID: " + routineId));
        
        availabilityMap.forEach((day, availability) -> {
            sportRoutine.updateAvailability(
                day,
                availability.morning(),
                availability.afternoon(),
                availability.evening()
            );
        });
        
        return sportRoutineRepository.save(sportRoutine);
    }
    

    public DailyAvailability getDailyAvailability(Long routineId, DayOfWeekEnum day) {
        SportRoutine sportRoutine = sportRoutineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Rotina esportiva não encontrada com ID: " + routineId));
        
        DailyAvailability availability = sportRoutine.getAvailabilityForDay(day);
        if (availability == null) {
            throw new EntityNotFoundException("Disponibilidade não encontrada para o dia: " + day);
        }
        
        return availability;
    }

    public SportRoutine saveSportRoutine(SportRoutine sportRoutine){
        return sportRoutineRepository.save(sportRoutine);
    }
}
