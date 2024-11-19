package org.example.service;

import org.example.exceptions.ExceptionNotFound;
import org.example.model.PontoColeta;

import java.sql.SQLException;
import java.util.List;

public interface PontoColetaService {

    PontoColeta getPontoColetaById(Long id) throws SQLException;
    List<PontoColeta> getAllPontoColeta() throws SQLException;
    PontoColeta createPontoColeta(PontoColeta pontoColeta) throws SQLException;
    void updatePontoColeta(PontoColeta pontoColeta) throws SQLException;
    void deletePontoColeta(Long id) throws SQLException, ExceptionNotFound;
}
