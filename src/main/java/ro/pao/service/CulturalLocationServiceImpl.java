package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.model.CulturalLocation;
import ro.pao.repository.LocationRepository;
import ro.pao.service.LocationService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class CulturalLocationServiceImpl implements LocationService<CulturalLocation> {

    private final LocationRepository<CulturalLocation> culturalLocationRepository;

    @Override
    public Optional<CulturalLocation> getById(UUID id) throws SQLException {

        Optional<CulturalLocation> culturalLocation = culturalLocationRepository.getObjectById(id);

        if(culturalLocation.isEmpty()) {
            throw new RuntimeException("CulturalLocation not found!");
        }

        return culturalLocation;

    }

    @Override
    public Optional<CulturalLocation> getByCapacity(Integer capacity) throws SQLException {

        Optional<CulturalLocation> culturalLocation = culturalLocationRepository.getObjectByCapacity(capacity);

        if(culturalLocation.isEmpty()) {
            throw new RuntimeException("There is no CulturalLocation of this capacity.");
        }

        return culturalLocation;

    }

    @Override
    public Map<UUID, CulturalLocation> getAllFromMap() {

        return listToMap(culturalLocationRepository.getAll());

    }

    @Override
    public Map<UUID, CulturalLocation> getAllFromMapWithCondition() {

        return listToMap(culturalLocationRepository.getAll().stream()
                .filter(culturalLocation -> culturalLocation.getAddress().contains("Bucuresti"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CulturalLocation> culturalLocationMap) throws SQLException {

        culturalLocationRepository.addAllFromGivenList(culturalLocationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(CulturalLocation culturalLocation) throws SQLException {

        culturalLocationRepository.addNewObject(culturalLocation);

    }

    @Override
    public void removeElementById(UUID id) {

        culturalLocationRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, CulturalLocation newCulturalLocation) {

        culturalLocationRepository.updateObjectById(id, newCulturalLocation);

    }

    @Override
    public Map<UUID, CulturalLocation> listToMap(List<CulturalLocation> culturalLocationList) {

        Map<UUID, CulturalLocation> culturalLocationMap = new HashMap<>();

        for (CulturalLocation culturalLocation : culturalLocationList) {

            culturalLocationMap.put(culturalLocation.getId(), culturalLocation);

        }

        return culturalLocationMap;

    }

}
