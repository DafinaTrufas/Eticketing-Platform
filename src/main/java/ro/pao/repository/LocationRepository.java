package ro.pao.repository;

import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsEvent;
import ro.pao.model.abstracts.Event;
import ro.pao.model.abstracts.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository <T extends Location> extends Repository<T> {

    Optional<T> getObjectByCapacity (Integer capacity) throws SQLException;

}
