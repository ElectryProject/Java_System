package org.example.dao;

import org.example.model.Residuo;

import java.sql.Connection;
import java.util.List;

public interface ResiduoDao {

    Residuo findById(Long id);
    List<Residuo> findAll();
    Residuo createResiduo(Residuo residuo, Connection connection);
    void updateResiduo(Residuo residuo, Connection connection);
    void deleteById(Long id, Connection connection);
}
