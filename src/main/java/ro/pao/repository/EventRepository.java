package ro.pao.repository;

import ro.pao.model.CulturalEvent;
import ro.pao.model.abstracts.Event;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRepository <T extends Event> extends Repository<T> {

    Optional<T> getObjectByLocation(UUID id) throws SQLException;

}
