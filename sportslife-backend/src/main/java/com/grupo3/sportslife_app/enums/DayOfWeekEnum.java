package com.grupo3.sportslife_app.enums;

public enum DayOfWeekEnum {
    MONDAY("Segunda-feira"),
    TUESDAY("Terça-feira"),
    WEDNESDAY("Quarta-feira"),
    THURSDAY("Quinta-feira"),
    FRIDAY("Sexta-feira"),
    SATURDAY("Sábado"),
    SUNDAY("Domingo");
    
    private final String displayName;
    
    DayOfWeekEnum(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}