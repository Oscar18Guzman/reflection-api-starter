package org.basecampcodingacademy.reflections;

import java.time.LocalDate;

public class OneReflectionPerDay extends Exception {
    public LocalDate date;

    public OneReflectionPerDay(LocalDate date) {
        this.date = date;
    }
}