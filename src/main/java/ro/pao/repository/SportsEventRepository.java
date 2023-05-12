package ro.pao.repository;

import ro.pao.model.CulturalEvent;
import ro.pao.model.SportsEvent;
import ro.pao.model.enums.CulturalEventType;
import ro.pao.model.enums.SportsEventType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SportsEventRepository {

    Optional<SportsEvent> getObjectById(UUID id) throws SQLException;

    Optional<SportsEvent> getObjectByLocation(UUID id) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, SportsEvent newObject);

    void addNewObject(SportsEvent sportsEvent);

    List<SportsEvent> getAll();

    void addAllFromGivenList(List<SportsEvent> sportsEventList);

}
