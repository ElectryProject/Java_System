package org.example.dao;

import oracle.jdbc.OracleType;
import org.example.connection.DatabaseConnectionFactory;
import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UsuarioDaoImpl implements UsuarioDao {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public Usuario findById(Long id) {
        String sql = "SELECT * FROM T_USUARIO WHERE ID_USUARIO = ?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }


        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o Usuário");
        }
        return null;
    }

    @Override
    public Usuario findbyName(String nome) {
        String sql = "SELECT * FROM T_USUARIO WHERE NM_COMPLETO = ?";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection()){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, nome);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToUsuario(rs);
            }

        } catch (SQLException e) {
            logger.warning("não foi possível recuperar o Usuário");
        }
        return null;
    }


    @Override
    public List<Usuario> findAll() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM T_USUARIO";
        try(Connection connection = DatabaseConnectionFactory.create().getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){

            while(rs.next()){
                usuarios.add(mapResultSetToUsuario(rs));
            }

        }catch (SQLException e){
            logger.warning("não foi possível recuperar os registros de Usuário.");
        }
        return usuarios;
    }

    @Override
    public Usuario createUsuario(Usuario usuario, Connection connection) {
        String sql = "BEGIN INSERT INTO T_USUARIO(NM_COMPLETO, DS_EMAIL, DS_SENHA, NR_CPF, IDADE, NR_TELEFONE, NR_CEP, DS_ENDERECO) VALUES(?,?,?,?,?,?,?,?) RETURNING ID_USUARIO INTO?; END;";
        try{
            CallableStatement call = connection.prepareCall(sql);
            call.setString(1, usuario.getNome());
            call.setString(2, usuario.getEmail());
            call.setString(3, usuario.getSenha());
            call.setLong(4, usuario.getNumeroCpf());
            call.setInt(5, usuario.getIdade());
            call.setLong(6, usuario.getNumeroTelefone());
            call.setString(7, usuario.getNumeroCep());
            call.setString(8, usuario.getEndereco());
            call.registerOutParameter(9, OracleType.NUMBER);

            int linhasAlteradas = call.executeUpdate();
            long id = call.getLong(9);
            if (linhasAlteradas == 0 || id==0) {
                throw new ExceptionNotCreated();
            }
            usuario.setId(id);
        } catch (SQLException e) {
            logger.warning("não foi possível inserir o registro.");
        }


        return usuario;
    }

    @Override
    public void updateUsuario(Usuario usuario, Connection connection)  {
        String sql = "UPDATE T_USUARIO SET NM_COMPLETO=?, DS_EMAIL=?, DS_SENHA=?, NR_CPF=?, IDADE=?, NR_TELEFONE=?, NR_CEP=?, DS_ENDERECO=? WHERE ID_USUARIO=?";
        try{
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setLong(4, usuario.getNumeroCpf());
            stmt.setInt(5, usuario.getIdade());
            stmt.setLong(6, usuario.getNumeroTelefone());
            stmt.setString(7, usuario.getNumeroCep());
            stmt.setString(8, usuario.getEndereco());
            stmt.setLong(9, usuario.getId());

            stmt.executeUpdate();
        }catch (SQLException e){
            logger.warning("não foi possível atualizar o registro");
        }

    }

    @Override
    public void deleteById(Long id, Connection connection) {
        String sql = "DELETE FROM T_USUARIO WHERE ID_USUARIO = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setLong(1, id);
            int linhasAlteradas = stmt.executeUpdate();
            if (linhasAlteradas == 0) {
                throw new ExceptionNotFound(id);
            }
        } catch (SQLException e) {
            logger.warning("não foi possível excluir o registro.");
        }

    }

    private Usuario mapResultSetToUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("ID_USUARIO"));
        usuario.setNome(rs.getString("NM_COMPLETO"));
        usuario.setEmail(rs.getString("DS_EMAIL"));
        usuario.setSenha(rs.getString("DS_SENHA"));
        usuario.setNumeroCpf(rs.getLong("NR_CPF"));
        usuario.setIdade(rs.getInt("IDADE"));
        usuario.setNumeroTelefone(rs.getLong("NR_TELEFONE"));
        usuario.setNumeroCep(rs.getString("NR_CEP"));
        usuario.setEndereco(rs.getString("DS_ENDERECO"));
        return usuario;



    }
}
