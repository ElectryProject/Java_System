package org.example.dao;

import org.example.model.PontoColeta;

import java.sql.Connection;
import java.util.List;

public interface PontoColetaDao {

    PontoColeta findById(Long id);
    List<PontoColeta> findAll();
    PontoColeta createPontoColeta(PontoColeta pontoColeta, Connection connection);
    void updatePontoColeta(PontoColeta pontoColeta, Connection connection);
    void deleteById(Long id, Connection connection);
}
