package com.tutorial.jdbctemplate.repository;

import com.tutorial.jdbctemplate.model.Tutorial;

import java.util.List;

public interface TutorialRepository {
    int save(Tutorial book);
    int update(Tutorial book);

    Tutorial findById(Long id);
    int deletById(Long id);
    List<Tutorial> findAll();
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);
    int deletAll();
}
