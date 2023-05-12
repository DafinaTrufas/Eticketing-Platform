package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.pao.model.CulturalLocation;
import ro.pao.model.CulturalLocation;
import ro.pao.model.MailInformation;
import ro.pao.model.enums.CulturalLocationType;
import ro.pao.repository.CulturalLocationRepository;
import ro.pao.service.CardService;
import ro.pao.service.CulturalLocationService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CulturalLocationServiceImpl implements CulturalLocationService {

    private final CulturalLocationRepository culturalLocationRepository;

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
    public void addAllFromGivenMap(Map<UUID, CulturalLocation> culturalLocationMap) {

        culturalLocationRepository.addAllFromGivenList(culturalLocationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(CulturalLocation culturalLocation) {

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
