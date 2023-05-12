package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.model.SportsLocation;
import ro.pao.repository.LocationRepository;
import ro.pao.service.LocationService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class SportsLocationServiceImpl implements LocationService<SportsLocation> {

    private final LocationRepository<SportsLocation> sportsLocationRepository;

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
    public void addAllFromGivenMap(Map<UUID, SportsLocation> sportsLocationMap) throws SQLException {

        sportsLocationRepository.addAllFromGivenList(sportsLocationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(SportsLocation sportsLocation) throws SQLException {

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
