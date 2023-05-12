package ro.pao.repository;

import ro.pao.model.SportsEvent;
import ro.pao.model.abstracts.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository {

    Optional<Location> getObjectById(UUID id) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, Location newObject);

    void addNewObject(Location location);

    List<Location> getAll();

    void addAllFromGivenList(List<Location> locationList);

}
