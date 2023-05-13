package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.CulturalLocation;
import ro.pao.repository.LocationRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class CulturalLocationServiceImpl implements LocationService<CulturalLocation> {

    private final LocationRepository<CulturalLocation> culturalLocationRepository;

    private static final Logger logger = Logger.getGlobal();

    @Override
    public Optional<CulturalLocation> getById(UUID id) throws SQLException {

        Optional<CulturalLocation> culturalLocation = Optional.empty();

        try {

            culturalLocation = culturalLocationRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

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

        Iterator<CulturalLocation> culturalLocationIterator = culturalLocationList.iterator();

        while (culturalLocationIterator.hasNext()) {

            CulturalLocation culturalLocation = culturalLocationIterator.next();

            culturalLocationMap.put(culturalLocation.getId(), culturalLocation);

        }

        return culturalLocationMap;

    }

}
