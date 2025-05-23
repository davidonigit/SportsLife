package com.grupo3.sportslife_app.dto;

import com.grupo3.sportslife_app.enums.DayOfWeekEnum;

public record DailyAvailabilityDTO(
    Long id,
     DayOfWeekEnum dayOfWeek,
      boolean morningAvailable,
       boolean afternoonAvailable,
        boolean eveningAvailable
    ) {}
