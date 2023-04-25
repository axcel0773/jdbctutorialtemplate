package com.tutorial.jdbctemplate.repository.impl;

import com.tutorial.jdbctemplate.model.Tutorial;
import com.tutorial.jdbctemplate.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTutorialRepository implements TutorialRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int save(Tutorial tutorial) {
        return jdbcTemplate.update(
                "INSERT INTO tutorials" +
                        "(title, description, published)" +
                        "VALUES(?,?,?)",
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.isPublished(),
                tutorial.getId()
        );
    }

    @Override
    public int update(Tutorial tutorial) {
        return jdbcTemplate.update(
                "UPDATE tutorials SET" +
                        "title=?" +
                        "description=?" +
                        "published=?" +
                        "WHERE id=?",
                tutorial.getTitle(),
                tutorial.getDescription(),
                tutorial.isPublished(),
                tutorial.getId()
        );
    }

    @Override
    public Tutorial findById(Long id) {
        try {
            Tutorial tutorial = jdbcTemplate.queryForObject(
                    "SELECT * FROM tutorials" +
                            "WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Tutorial.class), id);
            return tutorial;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    @Override
    public int deletById(Long id) {
        return jdbcTemplate.update("DELETE FROM tutorials" +
                "WHERE id=?", id);
    }

    @Override
    public List<Tutorial> findAll() {
        return jdbcTemplate.query("SELECT * FROM tutorials",
                BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public List<Tutorial> findByPublished(boolean published) {
        return jdbcTemplate.query("SELECT FROM tutorials WHERE published=?",
                BeanPropertyRowMapper.newInstance(Tutorial.class), published);
    }

    @Override
    public List<Tutorial> findByTitleContaining(String title) {
        String q = "SELECT * FROM tutorials" +
                    "WHERE title LIKE '%" +
                    title + "%'";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Tutorial.class));
    }

    @Override
    public int deletAll() {
        return jdbcTemplate.update("DELETE FROM tutorials");
    }
}
