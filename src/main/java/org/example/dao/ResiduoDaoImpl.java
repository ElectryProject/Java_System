package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Residuo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ResiduoDaoImpl implements ResiduoDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Residuo findById(Long id) {
        String sql = "SELECT * FROM T_RESIDUO WHERE ID_RESIDUO = ?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return mapResultSetResiduo(rs);
            }
        }catch(SQLException e){
            logger.warning("não foi possível recuperar o Resíduo no banco");
        }
        return null;
    }

    @Override
    public List<Residuo> findAll() {
        List<Residuo> residuos = new ArrayList<>();
        String sql = "SELECT * FROM T_RESIDUO";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                residuos.add(mapResultSetResiduo(rs));
            }
        }catch(SQLException e){
            logger.warning("não foi possível recuperar os registros de Resíduos");
        }
        return residuos;
    }

    @Override
    public Residuo createResiduo(Residuo residuo, Connection connection) {
        String sql = "BEGIN INSERT INTO T_RESIDUO(NM_PRODUTO, DS_CATEGORIA, DS_PONTUACAO) VALUES (?,?,?) RETURNING ID_RESIDUO INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, residuo.getNomeProduto());
            call.setString(2, residuo.getCategoria());
            call.setInt(3, residuo.getPontuacao());
            call.registerOutParameter(4, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(4);
            if(linhasAlteradas == 0 || id == 0){
                throw new ExceptionNotCreated();
            }
            residuo.setId(id);
        } catch (SQLException e) {
            logger.warning("não foi possível inserir o registro");
        }
        return residuo;
    }

    @Override
    public void updateResiduo(Residuo residuo, Connection connection) {
        String sql = "UPDATE T_RESIDUO SET NM_PRODUTO=?, DS_CATEGORIA=?, DS_PONTUACAO=? WHERE ID_RESIDUO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, residuo.getNomeProduto());
            stmt.setString(2, residuo.getCategoria());
            stmt.setInt(3, residuo.getPontuacao());
            stmt.setLong(4, residuo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warning("não foi possível atualizar o Resíduo");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_RESIDUO WHERE ID_RESIDUO = ?";
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

    private Residuo mapResultSetResiduo(ResultSet rs) throws SQLException {
        Residuo residuo = new Residuo();
        residuo.setId(rs.getLong("ID_RESIDUO"));
        residuo.setNomeProduto(rs.getString("NM_PRODUTO"));
        residuo.setCategoria(rs.getString("DS_CATEGORIA"));
        residuo.setPontuacao(rs.getInt("DS_PONTUACAO"));
        return residuo;
    }
}
