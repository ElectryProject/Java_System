package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Mensagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MensagemDaoImpl implements MensagemDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());


    @Override
    public Mensagem findById(Long id) {
        String sql = "SELECT * FROM T_MENSAGEM WHERE ID_MENSAGEM=?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                return mapResultSetMensagem(rs);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar a Mensagem no banco");
        }
        return null;
    }

    @Override
    public List<Mensagem> findAll() {
        List<Mensagem> mensagens = new ArrayList<>();
        String sql = "SELECT * FROM T_MENSAGEM";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                mensagens.add(mapResultSetMensagem(rs));
            }
        } catch (SQLException e) {
            logger.warning("não foi possível recuperar os registros de Mensagem");
        }
        return mensagens;
    }

    @Override
    public Mensagem createMensagem(Mensagem mensagem, Connection connection) {
        String sql = "BEGIN INSERT INTO T_MENSAGEM(NM_REMETENTE, DS_EMAIL, DS_ASSUNTO, DS_MENSAGEM) VALUES(?,?,?,?) RETURNING ID_MENSAGEM INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, mensagem.getNome());
            call.setString(2, mensagem.getEmail());
            call.setString(3, mensagem.getAssunto());
            call.setString(4, mensagem.getMensagem());
            call.registerOutParameter(5, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(5);
            if (linhasAlteradas == 0 || id == 0){
                throw new ExceptionNotCreated();
            }
            mensagem.setId(id);
        } catch (SQLException e) {
            logger.warning("não foi possível inserir o registro");
        }
        return mensagem;
    }

    @Override
    public void updateMensagem(Mensagem mensagem, Connection connection) {
        String sql = "UPDATE T_MENSAGEM SET NM_REMETENTE=?, DS_EMAIL=?, DS_ASSUNTO=?, DS_MENSAGEM=? WHERE ID_MENSAGEM=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, mensagem.getNome());
            stmt.setString(2, mensagem.getEmail());
            stmt.setString(3, mensagem.getAssunto());
            stmt.setString(4, mensagem.getMensagem());
            stmt.setLong(5, mensagem.getId());
            stmt.executeUpdate();
        }catch (SQLException e){
            logger.warning("não foi possível atualizar a Mensagem");
        }
    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_MENSAGEM WHERE ID_MENSAGEM=?";
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


    private Mensagem mapResultSetMensagem(ResultSet rs) throws SQLException {
        Mensagem mensagem = new Mensagem();
        mensagem.setId(rs.getLong("ID_MENSAGEM"));
        mensagem.setNome(rs.getString("NM_REMETENTE"));
        mensagem.setEmail(rs.getString("DS_EMAIL"));
        mensagem.setAssunto(rs.getString("DS_ASSUNTO"));
        mensagem.setMensagem(rs.getString("DS_MENSAGEM"));
        return mensagem;

    }
}
