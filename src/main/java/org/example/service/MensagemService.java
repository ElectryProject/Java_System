package org.example.service;

import org.example.exceptions.ExceptionNotCreated;
import org.example.exceptions.ExceptionNotFound;
import org.example.model.Mensagem;

import java.sql.SQLException;
import java.util.List;

public interface MensagemService {


    Mensagem getMensagemById(Long id) throws SQLException;
    List<Mensagem> getAllMensagem() throws SQLException;
    Mensagem createMensagem(Mensagem mensagem) throws SQLException, ExceptionNotCreated;
    void deleteMensagem(Long id) throws SQLException;
    void updateMensagem(Mensagem mensagem) throws SQLException, ExceptionNotFound;
}
