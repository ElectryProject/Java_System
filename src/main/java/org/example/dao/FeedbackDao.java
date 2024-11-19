package org.example.dao;

import org.example.model.Feedback;

import java.sql.Connection;
import java.util.List;

public interface FeedbackDao {

    Feedback findById(Long id);
    List<Feedback> findAll();
    Feedback createFeedback(Feedback feedback, Connection connection);
    void updateFeedback(Feedback feedback, Connection connection);
    void deleteById(Long id, Connection connection);
}
