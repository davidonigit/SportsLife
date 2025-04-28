package com.grupo3.sportslife_app.dto;

import com.grupo3.sportslife_app.enums.DayOfWeekEnum;

public record DailyAvailabilityDTO(DayOfWeekEnum day, boolean morning, boolean afternoon, boolean evening) {

}
