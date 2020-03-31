package org.basecampcodingacademy.reflections.controllers;

import org.basecampcodingacademy.reflections.ResponseForNonExistingReflection;
import org.basecampcodingacademy.reflections.db.AnswerRepository;
import org.basecampcodingacademy.reflections.db.ReflectionRepository;
import org.basecampcodingacademy.reflections.db.ResponseRepository;
import org.basecampcodingacademy.reflections.domain.Answer;
import org.basecampcodingacademy.reflections.domain.Reflection;
import org.basecampcodingacademy.reflections.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/reflections/{reflectionId}/responses")
public class ResponseController {
    @Autowired
    public ResponseRepository responses;
    @Autowired
    public AnswerRepository answers;
    @Autowired
    public ReflectionRepository reflections;


    @GetMapping
    public List<Response> index(@PathVariable Integer reflectionId, @RequestParam(defaultValue = "") String include) {
        var rs = responses.forReflection(reflectionId);
        if (include.equals("answers")) {
            for (Response r : rs) {
                r.answers = answers.forResponse(r.id);
            }
        }
        return rs;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response create(@RequestBody Response response, @PathVariable Integer reflectionId) throws ResponseForNonExistingReflection {
        response.reflectionId = reflectionId;
        if (!Objects.isNull(reflections.find(reflectionId))) {
            return responses.create(response);
        }
        throw new ResponseForNonExistingReflection(response.reflectionId);
    }


    @PatchMapping("/{id}")
    public Response update(@PathVariable Integer reflectionId, @PathVariable Integer id, @RequestBody Response response) {
        response.id = id;
        response.reflectionId = reflectionId;
        return responses.update(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        responses.delete(id);
    }
}