package ro.pao.service;

import ro.pao.model.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface Service <T> {

    Optional<T> getById(UUID id) throws SQLException;

    Map<UUID, T> getAllFromMap();

    Map<UUID, T> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, T> objectMap) throws SQLException;

    void addOnlyOne(T object) throws SQLException;

    void removeElementById(UUID id);

    void updateElementById(UUID id, T newObject);

    Map<UUID, T> listToMap(List<T> objectList);
    
}
