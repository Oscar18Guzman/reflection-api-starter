package org.basecampcodingacademy.reflections.db;

import org.basecampcodingacademy.reflections.domain.Answer;
import org.basecampcodingacademy.reflections.domain.Question;
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

    public Answer findone(Integer id) {
        return jdbc.queryForObject("SELECT id, responseId, questionId, content FROM answers WHERE id = ?", this::mapper, id);
    }

    public List<Answer> forResponse(Integer responseId) {
        return jdbc.query(
                "SELECT * FROM answers WHERE responseId = ?", this::mapper, responseId
        );
    }

    public Answer create(Answer answer) {
        var sql = "INSERT INTO answers (responseId, content) VALUES (?, ?) RETURNING *";
        return jdbc.queryForObject(
                sql,
                this::mapper,
                answer.responseId,
                answer.content
        );
    }


    public Answer find(Integer id) {
        return jdbc.queryForObject(
                "SELECT id, responseId, questionId, content FROM answers WHERE id = ?",
                this::mapper,
                id);
    }


    public Answer update(Answer answer) {
        return jdbc.queryForObject(
                "UPDATE answers SET content = ? WHERE id = ? RETURNING *",
                this::mapper, answer.content, answer.id);
    }

    public void delete(Integer id) {
        jdbc.query("DELETE FROM answers WHERE id = ? RETURNING id, responseId, questionId, content", this::mapper, id);
    }

    private Answer mapper(ResultSet resultSet, int i) throws SQLException {
        return new Answer(
                resultSet.getInt("id"),
                resultSet.getInt("responseId"),
                resultSet.getInt("questionId"),
                resultSet.getString("content")
        );
    }

    public List<Answer> findAllForResponse(Answer answer) {
        return jdbc.query("SELECT id, responseId, questionId, content FROM answers WHERE responseId = ?", this::mapper, answer.responseId);
    }
}