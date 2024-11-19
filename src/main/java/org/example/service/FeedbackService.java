package org.example.service;

import org.example.model.Feedback;

import java.sql.SQLException;
import java.util.List;

public interface FeedbackService {

    Feedback getFeedbackById(Long id) throws SQLException;
    List<Feedback> getAllFeedbacks() throws SQLException;
    Feedback createFeedback(Feedback feedback) throws SQLException;
    void updateFeedback(Feedback feedback) throws SQLException;
    void deleteFeedback(Long id) throws SQLException;
}
