package org.basecampcodingacademy.reflections.db;

import org.basecampcodingacademy.reflections.domain.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AnswerRepository {
    @Autowired
    public JdbcTemplate jdbc;

    public List<Answer> all() {
        return jdbc.query("SELECT id, responseId, questionId, content FROM answers", this::mapper);
    }

    public Answer create(Answer answer) {
        return jdbc.queryForObject(
                "INSERT INTO answers (questionId, responseId, content) VALUES (?, ?, ?) RETURNING id, responseId, questionId, content",
                this::mapper,
                answer.questionId,
                answer.responseId,
                answer.content

        );
    }

    public List<Answer> find(Answer answer) {
        return jdbc.query("SELECT id, responseId, questionId, content FROM answers WHERE responseId = ?", this::mapper, answer.responseId);
    }

    public Answer update(Answer answer) {
        return jdbc.queryForObject(
                "UPDATE answers SET content = ? WHERE id = ? RETURNING id, responseId, questionId, content",
                this::mapper, answer.content, answer.id);
    }

    public void delete(Integer id) {
        jdbc.query("DELETE FROM answers WHERE id = ? RETURNING id, responseId, questionId", this::mapper, id);
    }

    private Answer mapper(ResultSet resultSet, int i) throws SQLException {
        return new Answer(
                resultSet.getInt("id"),
                resultSet.getInt("responseId"),
                resultSet.getInt("questionId"),
                resultSet.getString("content")
        );
    }
}