package org.basecampcodingacademy.reflections.domain;

import java.util.List;

public class Response {
    public Integer id;
    public Integer reflectionId;
    public String userUsername;
    public List<Answer> answers;


    public Response() {
    }

    public Response(Integer id, Integer reflectionId, String userUsername, List<Answer> answers) {
        this.id = id;
        this.reflectionId = reflectionId;
        this.userUsername = userUsername;
        this.answers = answers;
    }

}