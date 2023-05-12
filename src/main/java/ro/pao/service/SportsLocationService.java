package ro.pao.service;

import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsLocation;
import ro.pao.model.enums.SportsLocationType;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface SportsLocationService {

    Optional<SportsLocation> getById(UUID id) throws SQLException;

    Optional<SportsLocation> getByCapacity(Integer capacity) throws SQLException;

    Map<UUID, SportsLocation> getAllFromMap();

    Map<UUID, SportsLocation> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, SportsLocation> sportsLocationMap);

    void addOnlyOne(SportsLocation sportsLocation);

    void removeElementById(UUID id);

    void updateElementById(UUID id, SportsLocation newElement);

    Map<UUID, SportsLocation> listToMap(List<SportsLocation> sportsLocationList);

}
