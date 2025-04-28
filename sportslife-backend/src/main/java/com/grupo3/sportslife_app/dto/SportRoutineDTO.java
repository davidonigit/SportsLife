package com.grupo3.sportslife_app.dto;

import com.grupo3.sportslife_app.enums.DayOfWeekEnum;

public record SportRoutineDTO(
    String name,
    DayOfWeekEnum monday,
    boolean mondayMorning,
    boolean mondayAfternoon,
    boolean mondayEvening,
    
    DayOfWeekEnum tuesday,
    boolean tuesdayMorning,
    boolean tuesdayAfternoon,
    boolean tuesdayEvening,
    
    DayOfWeekEnum wednesday,
    boolean wednesdayMorning,
    boolean wednesdayAfternoon,
    boolean wednesdayEvening,
    
    DayOfWeekEnum thursday,
    boolean thursdayMorning,
    boolean thursdayAfternoon,
    boolean thursdayEvening,
    
    DayOfWeekEnum friday,
    boolean fridayMorning,
    boolean fridayAfternoon,
    boolean fridayEvening,
    
    DayOfWeekEnum saturday,
    boolean saturdayMorning,
    boolean saturdayAfternoon,
    boolean saturdayEvening,
    
    DayOfWeekEnum sunday,
    boolean sundayMorning,
    boolean sundayAfternoon,
    boolean sundayEvening
) {}
