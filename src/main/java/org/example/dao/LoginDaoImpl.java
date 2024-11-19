package org.example.dao;


import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Login;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class LoginDaoImpl implements LoginDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Login findById(Long id) {
        String sql = "SELECT * FROM T_LOGIN WHERE ID_LOGIN=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetToLogin(rs);
            }
        }catch (SQLException e){
            logger.warning("não foi possível recuperar o Login do banco.");
        }
        return null;
    }

    @Override
    public List<Login> findAll() {
        List<Login> logins = new ArrayList<>();
        String sql = "SELECT * FROM T_LOGIN";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                logins.add(mapResultSetToLogin(rs));
            }
        } catch (SQLException e) {
            logger.warning("não foi possivel recuperar os registros de Login no banco.");
        }
        return logins;
    }

    @Override
    public Login createLogin(Login login, Connection connection) {
        String sql = "BEGIN INSERT INTO T_LOGIN (USERNAME, SENHA) VALUES (?, ?) RETURNING ID_LOGIN INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, login.getUsername());
            call.setString(2, login.getSenha());
            call.registerOutParameter(3, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(3);
            if (linhasAlteradas == 0 || id == 0) {
                throw new ExceptionNotCreated();
            }
            login.setId(id);
        }catch (SQLException e){
            logger.warning("não foi possível inserir o registro.");
        }
        return login;
    }

    @Override
    public void updateLogin(Login login, Connection connection) {
        String sql = "UPDATE T_LOGIN SET USERNAME=?, SENHA=? WHERE ID_LOGIN=?";
        try{
        PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getSenha());
            stmt.setLong(3, login.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o registro");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_LOGIN WHERE ID_LOGIN=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            int linhasAlteradas = stmt.executeUpdate();
            if (linhasAlteradas == 0){
                throw new ExceptionNotFound(id);
            }
        }catch (SQLException e){
            logger.warning("não foi possível excluir o registro. ");
        }
    }

    private Login mapResultSetToLogin(ResultSet rs) throws SQLException {
        Login login = new Login();
        login.setId(rs.getLong("ID_LOGIN"));
        login.setUsername(rs.getString("USERNAME"));
        login.setSenha(rs.getString("SENHA"));
        return login;
    }
}
