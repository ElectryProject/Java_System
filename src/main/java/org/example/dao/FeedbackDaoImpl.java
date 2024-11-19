package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FeedbackDaoImpl implements FeedbackDao{

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Feedback findById(Long id) {
        String sql = "SELECT * FROM T_FEEDBACK WHERE ID_FEEDBACK=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultSetToFeedback(rs);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o Feedback");
        }
        return null;
    }

    @Override
    public List<Feedback> findAll() {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM T_FEEDBACK";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                feedbacks.add(mapResultSetToFeedback(rs));
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar os registros de Feedback");
        }
        return feedbacks;
    }

    @Override
    public Feedback createFeedback(Feedback feedback, Connection connection) {
        String sql = "BEGIN INSERT INTO T_FEEDBACK(DS_COMENTARIO_AVALIATIVO, PONTUACAO_AVALIATIVA) VALUES (?,?) RETURNING ID_FEEDBACK INTO?;END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, feedback.getComentarioAvaliativo());
            call.setInt(2, feedback.getPontuacaoAvaliativa());
            call.registerOutParameter(3, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(3);
            if (linhasAlteradas == 0 || id == 0){
                throw new ExceptionNotCreated();
            }
            feedback.setId(id);
        } catch (SQLException e) {
            logger.warning("não foi possível inserir o registro.");
        }
        return feedback;
    }

    @Override
    public void updateFeedback(Feedback feedback, Connection connection) {
        String sql = "UPDATE T_FEEDBACK SET DS_COMENTARIO_AVALIATIVO=?, PONTUACAO_AVALIATIVA=? WHERE ID_FEEDBACK=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, feedback.getComentarioAvaliativo());
            stmt.setInt(2, feedback.getPontuacaoAvaliativa());
            stmt.setLong(3, feedback.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o registro");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_FEEDBACK WHERE ID_FEEDBACK=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            int linhasAlteradas = stmt.executeUpdate();
            if (linhasAlteradas == 0){
                throw new ExceptionNotFound(id);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível excluir o registro.");
        }

    }

    private Feedback mapResultSetToFeedback(ResultSet rs) throws SQLException{
        Feedback feedback = new Feedback();
        feedback.setId(rs.getLong("ID_FEEDBACK"));
        feedback.setComentarioAvaliativo(rs.getString("DS_COMENTARIO_AVALIATIVO"));
        feedback.setPontuacaoAvaliativa(rs.getInt("PONTUACAO_AVALIATIVA"));
        return feedback;
    }
}
