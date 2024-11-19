package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.PontoColeta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PontoColetaDaoImpl implements PontoColetaDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public PontoColeta findById(Long id) {
        String sql = "SELECT * FROM T_PONTO_COLETA WHERE ID_PONTO=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetToPontoColeta(rs);
            }
        }catch(SQLException e){
            logger.warning("não foi possível recuperar o Ponto de Coleta do banco.");
        }
        return null;
    }

    @Override
    public List<PontoColeta> findAll() {
        List<PontoColeta> pontoColetas = new ArrayList<>();
        String sql = "SELECT * FROM T_PONTO_COLETA";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                pontoColetas.add(mapResultSetToPontoColeta(rs));
            }
        }catch(SQLException e){
            logger.warning("não foi possível recuperar os registro de Ponto de Coleta.");
        }

        return pontoColetas;
    }

    @Override
    public PontoColeta createPontoColeta(PontoColeta pontoColeta, Connection connection) {
        String sql = "BEGIN INSERT INTO T_PONTO_COLETA(DS_LOCALIZACAO) VALUES (?) RETURNING ID_PONTO INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, pontoColeta.getLocalizacao());
            call.registerOutParameter(2, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(2);
            if (linhasAlteradas == 0 || id == 0) {
                throw new ExceptionNotCreated();
            }
            pontoColeta.setId(id);
        }catch (SQLException e){
            logger.warning("não foi possível inserir o registro");
        }
        return pontoColeta;
    }

    @Override
    public void updatePontoColeta(PontoColeta pontoColeta, Connection connection) {
        String sql = "UPDATE T_PONTO_COLETA SET DS_LOCALIZACAO=? WHERE ID_PONTO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pontoColeta.getLocalizacao());
            stmt.setLong(2, pontoColeta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o Ponto de Coleta");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_PONTO_COLETA WHERE ID_PONTO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            int linhasAlteradas = stmt.executeUpdate();
            if (linhasAlteradas == 0){
                throw new ExceptionNotFound(id);
            }
        }catch(SQLException e){
            logger.warning("não foi possível exluir o registro");
        }
    }



    private PontoColeta mapResultSetToPontoColeta(ResultSet rs) throws SQLException {
        PontoColeta pontoColeta = new PontoColeta();
        pontoColeta.setId(rs.getLong("ID_PONTO"));
        pontoColeta.setLocalizacao(rs.getString("DS_LOCALIZACAO"));
        return pontoColeta;
    }
}
