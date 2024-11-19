package org.example.service;

import org.example.exceptions.ExceptionNotFound;
import org.example.model.Descarte;

import java.sql.SQLException;
import java.util.List;

public interface DescarteService {

    Descarte getDescarteById(Long id) throws SQLException;
    List<Descarte> getAllDescarte() throws SQLException;
    Descarte createDescarte(Descarte descarte) throws SQLException;
    void updateDescarte(Descarte descarte) throws SQLException;
    void deleteDescarte(Long id) throws SQLException, ExceptionNotFound;
}
