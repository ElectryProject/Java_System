package org.example.dao;

import org.example.model.Mensagem;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface MensagemDao {

    Mensagem findById(Long id);
    List<Mensagem> findAll();
    Mensagem createMensagem(Mensagem mensagem, Connection connection);
    void updateMensagem(Mensagem mensagem, Connection connection);
    void deleteById(Long id, Connection connection);
}
