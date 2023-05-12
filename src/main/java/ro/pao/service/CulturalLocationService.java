package ro.pao.service;

import ro.pao.model.Client;
import ro.pao.model.CulturalLocation;
import ro.pao.model.enums.CulturalLocationType;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CulturalLocationService {

    Optional<CulturalLocation> getById(UUID id) throws SQLException;

    Optional<CulturalLocation> getByCapacity(Integer capacity) throws SQLException;

    Map<UUID, CulturalLocation> getAllFromMap();

    Map<UUID, CulturalLocation> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, CulturalLocation> culturalLocationMap);

    void addOnlyOne(CulturalLocation culturalLocation);

    void removeElementById(UUID id);

    void updateElementById(UUID id, CulturalLocation newCulturalLocation);

    Map<UUID, CulturalLocation> listToMap(List<CulturalLocation> culturalLocationList);

}
