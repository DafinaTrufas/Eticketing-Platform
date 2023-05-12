package ro.pao.repository;

import ro.pao.model.abstracts.Event;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository {

    Optional<Event> getObjectById(UUID id) throws SQLException;

    void deleteObjectById(UUID id) throws SQLException;

    void updateObjectById(UUID id, Event newObject);

    void addNewObject(Event exampleClass) throws SQLException;

    List<Event> getAll();

    void addAllFromGivenList(List<Event> exampleClassList) throws SQLException;

}
