package ro.pao.service;

import ro.pao.model.abstracts.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface LocationService <T extends Location> {

    Optional<T> getById(UUID id) throws SQLException;

    Optional<T> getByCapacity(Integer capacity) throws SQLException;

    Map<UUID, T> getAllFromMap();

    Map<UUID, T> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, T> locationMap);

    void addOnlyOne(T location);

    void removeElementById(UUID id);

    void updateElementById(UUID id, T newLocation);

    Map<UUID, T> listToMap(List<T> locationList);
    
}
