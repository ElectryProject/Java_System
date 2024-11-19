package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Descarte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DescarteDaoImpl implements DescarteDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Descarte findById(Long id) {
        String sql = "SELECT * FROM T_DESCARTE WHERE ID_DESCARTE=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetToDescarte(rs);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o Descarte do banco.");
        }
        return null;
    }

    @Override
    public List<Descarte> findAll() {
        List<Descarte> descartes = new ArrayList<Descarte>();
        String sql = "SELECT * FROM T_DESCARTE";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                descartes.add(mapResultSetToDescarte(rs));
            }
        }catch (SQLException e){
            logger.warning("não foi possível recuperar os registros de Descarte.");
        }
        return descartes;
    }

    @Override
    public Descarte createDescarte(Descarte descarte, Connection connection) {
        String sql = "BEGIN INSERT INTO T_DESCARTE (DS_PRODUTO, DT_DESCARTE) VALUES (?,?) RETURNING ID_DESCARTE INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, descarte.getProduto());
            call.setString(2, descarte.getDataDescarte());
            call.registerOutParameter(3, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(3);
            if (linhasAlteradas == 0 || id == 0) {
                throw new ExceptionNotCreated();
            }
            descarte.setId(id);
        }catch (SQLException e){
            logger.warning("não foi possível inserir o registro.");
        }
        return descarte;
    }

    @Override
    public void updateDescarte(Descarte descarte, Connection connection) {
        String sql = "UPDATE T_DESCARTE SET DS_PRODUTO=?, DT_DESCARTE=? WHERE ID_DESCARTE=?";
        try{
            PreparedStatement stmt= connection.prepareStatement(sql);
            stmt.setString(1, descarte.getProduto());
            stmt.setString(2, descarte.getDataDescarte());
            stmt.setLong(3, descarte.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o registro");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_DESCARTE WHERE ID_DESCARTE=?";
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

    private Descarte mapResultSetToDescarte(ResultSet rs) throws SQLException {
        Descarte descarte = new Descarte();
        descarte.setId(rs.getLong("ID_DESCARTE"));
        descarte.setProduto(rs.getString("DS_PRODUTO"));
        descarte.setDataDescarte(rs.getString("DT_DESCARTE"));
        return descarte;
    }
}
