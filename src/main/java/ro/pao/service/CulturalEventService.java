package ro.pao.service;

import ro.pao.model.Client;
import ro.pao.model.CulturalEvent;
import ro.pao.model.enums.CulturalEventType;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CulturalEventService {

    Optional<CulturalEvent> getById(UUID id) throws SQLException;

    Optional<CulturalEvent> getByLocation(UUID id) throws SQLException;

    Map<UUID, CulturalEvent> getAllFromMap();

    Map<UUID, CulturalEvent> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, CulturalEvent> culturalEventMap);

    void addOnlyOne(CulturalEvent culturalEvent) throws SQLException;

    void removeElementById(UUID id);

    void updateElementById(UUID id, CulturalEvent newCulturalEvent);

    Map<UUID, CulturalEvent> listToMap(List<CulturalEvent> culturalEventList);

}
