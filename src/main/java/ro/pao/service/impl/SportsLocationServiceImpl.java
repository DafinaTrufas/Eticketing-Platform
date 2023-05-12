package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.pao.model.SportsLocation;
import ro.pao.model.SportsLocation;
import ro.pao.model.MailInformation;
import ro.pao.model.enums.SportsLocationType;
import ro.pao.repository.SportsLocationRepository;
import ro.pao.service.CardService;
import ro.pao.service.SportsLocationService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SportsLocationServiceImpl implements SportsLocationService {

    private final SportsLocationRepository sportsLocationRepository;

    @Override
    public Optional<SportsLocation> getById(UUID id) throws SQLException {

        Optional<SportsLocation> sportsLocation = sportsLocationRepository.getObjectById(id);

        if(sportsLocation.isEmpty()) {
            throw new RuntimeException("SportsLocation not found!");
        }

        return sportsLocation;

    }

    @Override
    public Optional<SportsLocation> getByCapacity(Integer capacity) throws SQLException {

        Optional<SportsLocation> sportsLocation = sportsLocationRepository.getObjectByCapacity(capacity);

        if(sportsLocation.isEmpty()) {
            throw new RuntimeException("There is no SportsLocation of this capacity.");
        }

        return sportsLocation;

    }

    @Override
    public Map<UUID, SportsLocation> getAllFromMap() {

        return listToMap(sportsLocationRepository.getAll());

    }

    @Override
    public Map<UUID, SportsLocation> getAllFromMapWithCondition() {

        return listToMap(sportsLocationRepository.getAll().stream()
                .filter(sportsLocation -> sportsLocation.getAddress().contains("Bucuresti"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, SportsLocation> sportsLocationMap) {

        sportsLocationRepository.addAllFromGivenList(sportsLocationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(SportsLocation sportsLocation) {

        sportsLocationRepository.addNewObject(sportsLocation);

    }

    @Override
    public void removeElementById(UUID id) {

        sportsLocationRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, SportsLocation newSportsLocation) {

        sportsLocationRepository.updateObjectById(id, newSportsLocation);

    }

    @Override
    public Map<UUID, SportsLocation> listToMap(List<SportsLocation> sportsLocationList) {

        Map<UUID, SportsLocation> sportsLocationMap = new HashMap<>();

        for (SportsLocation sportsLocation : sportsLocationList) {

            sportsLocationMap.put(sportsLocation.getId(), sportsLocation);

        }

        return sportsLocationMap;

    }

}
