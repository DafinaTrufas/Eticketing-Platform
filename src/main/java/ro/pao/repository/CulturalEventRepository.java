package ro.pao.repository;

import ro.pao.mapper.CulturalEventMapper;
import ro.pao.model.CulturalEvent;
import ro.pao.model.abstracts.Event;
import ro.pao.model.enums.CulturalEventType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CulturalEventRepository {

    Optional<CulturalEvent> getObjectById(UUID id) throws SQLException;

    Optional<CulturalEvent> getObjectByLocation(UUID id) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, CulturalEvent newObject);

    void addNewObject(CulturalEvent culturalEvent) throws SQLException;

    List<CulturalEvent> getAll();

    void addAllFromGivenList(List<CulturalEvent> culturalEventList);

}
