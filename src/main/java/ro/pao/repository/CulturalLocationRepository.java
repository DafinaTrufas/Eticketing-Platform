package ro.pao.repository;

import ro.pao.model.CulturalLocation;
import ro.pao.model.abstracts.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CulturalLocationRepository {

    Optional<CulturalLocation> getObjectById(UUID id) throws SQLException;

    Optional<CulturalLocation> getObjectByCapacity(Integer capacity) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, CulturalLocation newObject);

    void addNewObject(CulturalLocation culturalLocation);

    List<CulturalLocation> getAll();

    void addAllFromGivenList(List<CulturalLocation> culturalLocationList);

}
