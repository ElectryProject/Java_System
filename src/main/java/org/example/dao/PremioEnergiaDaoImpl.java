package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.PremioEnergia;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PremioEnergiaDaoImpl implements PremioEnergiaDao{

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public PremioEnergia findById(Long id) {
        String sql = "SELECT * FROM T_PREMIO_ENERGIA WHERE ID_PREMIO=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultSetToPremioEnergia(rs);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o Prêmio Energia");
        }
        return null;
    }

    @Override
    public List<PremioEnergia> findAll() {
        List<PremioEnergia> premios = new ArrayList<>();
        String sql = "SELECT * FROM T_PREMIO_ENERGIA";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection();
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                premios.add(mapResultSetToPremioEnergia(rs));
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o registros de Prêmio Energia");
        }
        return premios;
    }

    @Override
    public PremioEnergia createPremio(PremioEnergia premioEnergia, Connection connection) {
        String sql = "BEGIN INSERT INTO T_PREMIO_ENERGIA(NM_PREMIO, PONTUACAO_PREMIO) VALUES (?,?) RETURNING ID_PREMIO INTO?;END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, premioEnergia.getNomePremio());
            call.setInt(2, premioEnergia.getValorPremio());
            call.registerOutParameter(3, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(3);
            if (linhasAlteradas == 0 || id == 0){
                throw new ExceptionNotCreated();
            }
            premioEnergia.setId(id);
        } catch (SQLException e) {
            logger.warning("não foi possível inserir o Prêmio Energia");
        }
        return premioEnergia;
    }

    @Override
    public void updatePremio(PremioEnergia premioEnergia, Connection connection) {
        String sql = "UPDATE T_PREMIO_ENERGIA SET NM_PREMIO=?, PONTUACAO_PREMIO=? WHERE ID_PREMIO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, premioEnergia.getNomePremio());
            stmt.setInt(2, premioEnergia.getValorPremio());
            stmt.setLong(3, premioEnergia.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o registro");
        }

    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_PREMIO_ENERGIA WHERE ID_PREMIO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            int linhasAlteradas = stmt.executeUpdate();
            if (linhasAlteradas == 0){
                throw new ExceptionNotFound(id);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível excluir o registro");
        }

    }

    private PremioEnergia mapResultSetToPremioEnergia(ResultSet rs) throws SQLException{
        PremioEnergia premioEnergia = new PremioEnergia();
        premioEnergia.setId(rs.getLong("ID_PREMIO"));
        premioEnergia.setNomePremio(rs.getString("NM_PREMIO"));
        premioEnergia.setValorPremio(rs.getInt("PONTUACAO_PREMIO"));
        return premioEnergia;
    }

}
