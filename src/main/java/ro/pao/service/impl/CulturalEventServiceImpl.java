package ro.pao.service.impl;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.pao.model.CulturalEvent;
import ro.pao.model.CulturalEvent;
import ro.pao.model.MailInformation;
import ro.pao.model.enums.CulturalEventType;
import ro.pao.repository.CulturalEventRepository;
import ro.pao.service.CardService;
import ro.pao.service.CulturalEventService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CulturalEventServiceImpl implements CulturalEventService {

    private final CulturalEventRepository culturalEventRepository;

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
    public void addAllFromGivenMap(Map<UUID, CulturalEvent> culturalEventMap) {

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
