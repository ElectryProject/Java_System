package org.example.service;

import org.example.connection.DatabaseConnectionFactory;
import org.example.dao.FeedbackDao;
import org.example.dao.FeedbackDaoImpl;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.exceptions.ExceptionNotUpdate;
import org.example.model.Feedback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

final class FeedbackServiceImpl implements FeedbackService{

    private FeedbackDao feedbackDao = new FeedbackDaoImpl();

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Feedback getFeedbackById(Long id) {
        return feedbackDao.findById(id);
    }

    @Override
    public List<Feedback> getAllFeedbacks() {
        return feedbackDao.findAll();
    }

    @Override
    public Feedback createFeedback(Feedback feedback) throws SQLException {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        try{
            feedback.validarPontuacao();
            feedbackDao.createFeedback(feedback, connection);
            connection.commit();
        } catch (SQLException | ExceptionNotCreated e) {
            logger.severe("Erro ao criar Feedback");
            connection.rollback();
            throw e;
        }
        return feedback;
    }

    @Override
    public void updateFeedback(Feedback feedback) throws ExceptionNotCreated  {
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            feedbackDao.updateFeedback(feedback, connection);
            connection.commit();
        } catch (SQLException e) {
            throw new ExceptionNotUpdate();
        }


    }

    @Override
    public void deleteFeedback(Long id) throws SQLException, ExceptionNotFound {
        Connection connection = DatabaseConnectionFactory.create().getConnection();
        this.feedbackDao.deleteById(id, connection);
        connection.commit();

    }
}
