package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import ro.pao.model.SportsEvent;
import ro.pao.repository.EventRepository;
import ro.pao.service.EventService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SportsEventServiceImpl implements EventService<SportsEvent> {

    private final EventRepository<SportsEvent> sportsEventRepository;

    @Override
    public Optional<SportsEvent> getById(UUID id) throws SQLException {

        Optional<SportsEvent> sportsEvent = sportsEventRepository.getObjectById(id);

        if(sportsEvent.isEmpty()) {
            throw new RuntimeException("SportsEvent not found!");
        }

        return sportsEvent;

    }

    @Override
    public Optional<SportsEvent> getByLocation(UUID id) throws SQLException {

        Optional<SportsEvent> sportsEvent = sportsEventRepository.getObjectByLocation(id);

        if(sportsEvent.isEmpty()) {
            throw new RuntimeException("There is no SportsEvent in this location.");
        }

        return sportsEvent;

    }

    @Override
    public Map<UUID, SportsEvent> getAllFromMap() {

        return listToMap(sportsEventRepository.getAll());

    }

    @Override
    public Map<UUID, SportsEvent> getAllFromMapWithCondition() {

        return listToMap(sportsEventRepository.getAll().stream()
                .filter(sportsEvent -> sportsEvent.getStage().contains("final"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, SportsEvent> sportsEventMap) throws SQLException {

        sportsEventRepository.addAllFromGivenList(sportsEventMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(SportsEvent sportsEvent) throws SQLException {

        sportsEventRepository.addNewObject(sportsEvent);

    }

    @Override
    public void removeElementById(UUID id) {

        sportsEventRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, SportsEvent newSportsEvent) {

        sportsEventRepository.updateObjectById(id, newSportsEvent);

    }

    @Override
    public Map<UUID, SportsEvent> listToMap(List<SportsEvent> sportsEventList) {

        Map<UUID, SportsEvent> sportsEventMap = new HashMap<>();

        for (SportsEvent sportsEvent : sportsEventList) {

            sportsEventMap.put(sportsEvent.getId(), sportsEvent);

        }

        return sportsEventMap;

    }

}
