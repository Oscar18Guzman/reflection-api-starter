package org.basecampcodingacademy.reflections.db;

import org.basecampcodingacademy.reflections.domain.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

@Repository
public class ResponseRepository {
    @Autowired
    public JdbcTemplate jdbc;

    public List<Response> forReflection(Integer reflectionid) {
        return jdbc.query("SELECT * FROM responses WHERE reflectionId = ?", this::mapper, reflectionid);
    }

    public Response create(Response response) {
        return jdbc.queryForObject(
                "INSERT INTO responses (userUsername, reflectionId) VALUES (?, ?) RETURNING id, userUsername, reflectionId",
                this::mapper,
                response.userUsername,
                response.reflectionId
        );
    }

    public Response update(Response response) {
        return jdbc.queryForObject(
                "UPDATE responses SET userUsername = ? WHERE id = ? RETURNING id, reflectionId",
                this::mapper, response.reflectionId, response.id);
    }

    public void delete(Integer id) {
        jdbc.query("DELETE FROM responses WHERE id = ? RETURNING id, reflectionId", this::mapper, id);
    }

    private Response mapper(ResultSet resultSet, int i) throws SQLException {
        return new Response(
                resultSet.getInt("id"),
                resultSet.getInt("reflectionId"),
                resultSet.getString("userUsername"),
                null
        );
    }
}