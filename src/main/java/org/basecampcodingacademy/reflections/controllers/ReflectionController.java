package org.basecampcodingacademy.reflections.controllers;

import org.basecampcodingacademy.reflections.OneReflectionPerDay;
import org.basecampcodingacademy.reflections.ResponseForNonExistingReflection;
import org.basecampcodingacademy.reflections.db.AnswerRepository;
import org.basecampcodingacademy.reflections.db.QuestionRepository;
import org.basecampcodingacademy.reflections.db.ReflectionRepository;
import org.basecampcodingacademy.reflections.domain.Answer;
import org.basecampcodingacademy.reflections.domain.Reflection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reflections")
public class ReflectionController {
    @Autowired
    public ReflectionRepository reflections;
    @Autowired
    public QuestionRepository questions;

    @GetMapping
    public List<Reflection> index() {
        return reflections.all();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reflection create(@RequestBody Reflection reflection) throws OneReflectionPerDay {
        if (Objects.isNull(reflections.find(reflection.date))) {
            return reflections.create(reflection);
        }
        throw new OneReflectionPerDay(reflection.date);
    }

    @GetMapping("/today")
    public Reflection today(@RequestParam(defaultValue = "") String include) {
        var reflection = reflections.find(LocalDate.now());
        if (include.equals("questions")) {
            reflection.questions = questions.forReflection(reflection.id);
        }
        return reflection;
    }


    @PatchMapping("/{id}")
    public Reflection update(@PathVariable Integer id, @RequestBody Reflection reflection) {
        reflection.id = id;
        return reflections.update(reflection);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        reflections.delete(id);
    }
}