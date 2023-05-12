package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.model.CulturalEvent;
import ro.pao.repository.EventRepository;
import ro.pao.service.EventService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class CulturalEventServiceImpl implements EventService<CulturalEvent> {

    private final EventRepository<CulturalEvent> culturalEventRepository;

    @Override
    public Optional<CulturalEvent> getById(UUID id) throws SQLException {

        Optional<CulturalEvent> culturalEvent = culturalEventRepository.getObjectById(id);

        if(culturalEvent.isEmpty()) {
            throw new RuntimeException("CulturalEvent not found!");
        }

        return culturalEvent;

    }

    @Override
    public Optional<CulturalEvent> getByLocation(UUID id) throws SQLException {

        Optional<CulturalEvent> culturalEvent = culturalEventRepository.getObjectByLocation(id);

        if(culturalEvent.isEmpty()) {
            throw new RuntimeException("There is no CulturalEvent in this location.");
        }

        return culturalEvent;

    }

    @Override
    public Map<UUID, CulturalEvent> getAllFromMap() {

        return listToMap(culturalEventRepository.getAll());

    }

    @Override
    public Map<UUID, CulturalEvent> getAllFromMapWithCondition() {

        return listToMap(culturalEventRepository.getAll().stream()
                .filter(culturalEvent -> culturalEvent.getTitle().contains("spectacol"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CulturalEvent> culturalEventMap) throws SQLException {

        culturalEventRepository.addAllFromGivenList(culturalEventMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(CulturalEvent culturalEvent) throws SQLException {

        culturalEventRepository.addNewObject(culturalEvent);

    }

    @Override
    public void removeElementById(UUID id) {

        culturalEventRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, CulturalEvent newCulturalEvent) {

        culturalEventRepository.updateObjectById(id, newCulturalEvent);

    }

    @Override
    public Map<UUID, CulturalEvent> listToMap(List<CulturalEvent> culturalEventList) {

        Map<UUID, CulturalEvent> culturalEventMap = new HashMap<>();

        for (CulturalEvent culturalEvent : culturalEventList) {

            culturalEventMap.put(culturalEvent.getId(), culturalEvent);

        }

        return culturalEventMap;

    }

}
