package ro.pao.service;

import ro.pao.model.abstracts.Location;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public sealed interface LocationService <T extends Location> extends Service<T> permits CulturalLocationServiceImpl, LocationServiceImpl, SportsLocationServiceImpl {

    Optional<T> getByCapacity(Integer capacity) throws SQLException;
    
}
