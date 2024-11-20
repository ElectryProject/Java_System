package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Token;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class TokenDaoImpl implements TokenDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Token findById(Long id) {
        String sql = "SELECT * FROM T_TOKEN WHERE ID_TOKEN=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultSetToToken(rs);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o Token do banco.");
        }
        return null;
    }

    @Override
    public List<Token> findAll() {
        List<Token> tokens = new ArrayList<>();
        String sql = "SELECT * FROM T_TOKEN";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                tokens.add(mapResultSetToToken(rs));
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar os registros de Token.");
        }
        return tokens;
    }

    @Override
    public Token createToken(Token token, Connection connection) {
        String sql = "BEGIN INSERT INTO T_TOKEN (CD_TOKEN) VALUES (?) RETURNING ID_TOKEN INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, token.getCodigoToken());
            call.registerOutParameter(2, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(2);
            if(linhasAlteradas == 0 || id == 0){
                throw new ExceptionNotCreated();

            }
            token.setId(id);
        }catch (SQLException e){
            logger.warning("não foi possível inserir o registro.");
        }
        return token;
    }

    @Override
    public void updateToken(Token token, Connection connection) {
        String sql = "UPDATE T_TOKEN SET CD_TOKEN=? WHERE ID_TOKEN=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, token.getCodigoToken());
            stmt.setLong(2, token.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o registro");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_TOKEN WHERE ID_TOKEN=?";
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

    private Token mapResultSetToToken(ResultSet rs) throws SQLException {
        Token token = new Token();
        token.setId(rs.getLong("ID_TOKEN"));
        token.setCodigoToken(rs.getString("CD_TOKEN"));
        return token;
    }
}
