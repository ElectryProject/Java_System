package org.example.dao;

import org.example.model.Descarte;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DescarteDao {

    Descarte findById(Long id);
    List<Descarte> findAll();
    Descarte createDescarte(Descarte descarte, Connection connection);
    void updateDescarte(Descarte descarte, Connection connection) ;
    void deleteById(Long id, Connection connection);
}
