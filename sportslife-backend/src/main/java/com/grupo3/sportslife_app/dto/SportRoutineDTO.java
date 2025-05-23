package com.grupo3.sportslife_app.dto;

import java.util.ArrayList;


public record SportRoutineDTO(
    String sport,
    ArrayList<DailyAvailabilityDTO> weeklyAvailability
) {}
