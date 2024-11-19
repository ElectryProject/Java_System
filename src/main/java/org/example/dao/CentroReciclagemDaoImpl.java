package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.CentroReciclagem;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CentroReciclagemDaoImpl implements CentroReciclagemDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public CentroReciclagem findById(Long id) {
        String sql = "SELECT * FROM T_CENTRO_RECICLAGEM WHERE ID_CENTRO=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetCentroReciclagem(rs);
            }
        }catch (SQLException e){
            logger.warning("não foi possível recuperar o Centro de Reciclagem no banco.");
        }
        return null;
    }

    @Override
    public List<CentroReciclagem> findAll() {
        List<CentroReciclagem> centrosReciclagem = new ArrayList<>();
        String sql = "SELECT * FROM T_CENTRO_RECICLAGEM";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                centrosReciclagem.add(mapResultSetCentroReciclagem(rs));
            }
        }catch (SQLException e){
            logger.warning("não foi possível recuperar os registros de Centro de Reciclagem");
        }
        return centrosReciclagem;
    }

    @Override
    public CentroReciclagem createCentroReciclagem(CentroReciclagem centroReciclagem, Connection connection) {
        String sql = "BEGIN INSERT INTO T_CENTRO_RECICLAGEM(NM_CENTRO, VL_RESGATADO) VALUES(?,?) RETURNING ID_CENTRO INTO?;END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, centroReciclagem.getNomeCentro());
            call.setDouble(2, centroReciclagem.getValorResgatado());
            call.registerOutParameter(3, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(3);
            if(linhasAlteradas == 0 || id == 0){
                throw new ExceptionNotCreated();

            }
            centroReciclagem.setId(id);
        }catch (SQLException e){
            logger.warning("não foi possível inserir o registro");
        }
        return centroReciclagem;
    }

    @Override
    public void updateCentroReciclagem(CentroReciclagem centroReciclagem, Connection connection) {
            String sql = "UPDATE T_CENTRO_RECICLAGEM SET NM_CENTRO=?, VL_RESGATADO=? WHERE ID_CENTRO=?";
            try{
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, centroReciclagem.getNomeCentro());
                stmt.setDouble(2, centroReciclagem.getValorResgatado());
                stmt.setLong(3, centroReciclagem.getId());
                stmt.executeUpdate();
            } catch (SQLException e) {
               logger.warning("não foi possível atualizar o registro");
            }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_CENTRO_RECICLAGEM WHERE ID_CENTRO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            int linhasAlteradas = stmt.executeUpdate();
            if (linhasAlteradas == 0){
                throw new ExceptionNotFound(id);
            }
        }catch (SQLException e){
            logger.warning("não foi possível excluir o registro");
        }

    }


    private CentroReciclagem mapResultSetCentroReciclagem(ResultSet rs) throws SQLException {
        CentroReciclagem centroReciclagem = new CentroReciclagem();
        centroReciclagem.setId(rs.getLong("ID_CENTRO"));
        centroReciclagem.setNomeCentro(rs.getString("NM_CENTRO"));
        centroReciclagem.setValorResgatado(rs.getDouble("VL_RESGATADO"));
        return centroReciclagem;
    }

}
