package ro.pao.repository;

import ro.pao.model.abstracts.Event;
import ro.pao.repository.CulturalEventRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public sealed interface EventRepository <T extends Event> extends Repository<T> permits CulturalEventRepositoryImpl, EventRepositoryImpl, SportsEventRepositoryImpl {

    Optional<T> getObjectByLocation(UUID id) throws SQLException;

}
