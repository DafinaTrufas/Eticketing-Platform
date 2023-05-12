package ro.pao.service;

import ro.pao.model.abstracts.Event;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface EventService <T extends Event> {

    Optional<T> getById(UUID id);

    Optional<T> getByLocation(UUID id);

    Map<UUID, T> getAllFromMap();

    Map<UUID, T> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, T> eventMap) throws SQLException;

    void addOnlyOne(T event) throws SQLException;

    void removeElementById(UUID id);

    void updateElementById(UUID id, T newEvent);

    Map<UUID, T> listToMap(List<T> eventList);

}
