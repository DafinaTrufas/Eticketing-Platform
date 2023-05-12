package ro.pao.repository;

import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsLocation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SportsLocationRepository {

    Optional<SportsLocation> getObjectById(UUID id) throws SQLException;

    Optional<SportsLocation> getObjectByCapacity(Integer capacity) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, SportsLocation newObject);

    void addNewObject(SportsLocation sportsLocation);

    List<SportsLocation> getAll();

    void addAllFromGivenList(List<SportsLocation> sportsLocationList);

}
