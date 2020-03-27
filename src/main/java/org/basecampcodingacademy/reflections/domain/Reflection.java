package org.basecampcodingacademy.reflections.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class Reflection {

    public Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date;
    public List<Question> questions; // You will have to change this line eventually

    public Reflection() {
    }

    public Reflection(Integer id, LocalDate date, List<Question> questions) {
        this.id = id;
        this.date = date;
        this.questions = questions;
    }
}