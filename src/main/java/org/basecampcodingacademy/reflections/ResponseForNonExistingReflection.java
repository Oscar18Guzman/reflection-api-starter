package org.basecampcodingacademy.reflections;


public class ResponseForNonExistingReflection extends Exception {
    public Integer reflectionId;

    public ResponseForNonExistingReflection(Integer reflectionId) {
        this.reflectionId = reflectionId;
    }
}