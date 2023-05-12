package ro.pao.service;

import ro.pao.model.abstracts.Event;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public sealed interface EventService <T extends Event> extends Service<T> permits CulturalEventServiceImpl, EventServiceImpl, SportsEventServiceImpl {

    Optional<T> getByLocation(UUID id) throws SQLException;

}
