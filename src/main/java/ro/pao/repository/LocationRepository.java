package ro.pao.repository;

import ro.pao.model.abstracts.Location;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface LocationRepository <T extends Location> extends Repository<T> permits LocationRepositoryImpl, CulturalLocationRepositoryImpl, SportsLocationRepositoryImpl {

    Optional<T> getObjectByCapacity (Integer capacity) throws SQLException;

}
