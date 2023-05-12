package ro.pao.repository;

import lombok.RequiredArgsConstructor;
import ro.pao.model.CulturalEvent;
import ro.pao.model.SportsEvent;
import ro.pao.model.abstracts.Event;
import ro.pao.repository.EventRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public non-sealed class EventRepositoryImpl implements EventRepository<Event> {

    private final EventRepository<CulturalEvent> culturalEventRepository;

    private final EventRepository<SportsEvent> sportsEventRepository;

    @Override
    public Optional<Event> getObjectById(UUID id) throws SQLException {

        try {

            culturalEventRepository.getObjectById(id);

        } catch (RuntimeException e) {

            sportsEventRepository.getObjectById(id);

        }

        return Optional.empty();

    }

    @Override
    public Optional<Event> getObjectByLocation(UUID id) throws SQLException {

        try {

            culturalEventRepository.getObjectByLocation(id);

        } catch (RuntimeException e) {

            sportsEventRepository.getObjectByLocation(id);

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        try {

            culturalEventRepository.deleteObjectById(id);

        } catch (RuntimeException e) {

            sportsEventRepository.deleteObjectById(id);

        }

    }

    @Override
    public void updateObjectById(UUID id, Event newObject) {

        if (newObject instanceof CulturalEvent culturalEvent) {

            culturalEventRepository.updateObjectById(id, culturalEvent);

        }

        else {

            sportsEventRepository.updateObjectById(id, (SportsEvent) newObject);

        }

    }

    @Override
    public void addNewObject (Event event) throws SQLException {

        if (event instanceof CulturalEvent culturalEvent) {

            culturalEventRepository.addNewObject(culturalEvent);

        }

        else {

            sportsEventRepository.addNewObject((SportsEvent) event);

        }

    }

    @Override
    public List<Event> getAll() {

        try {

            culturalEventRepository.getAll();

        } catch (RuntimeException e) {

            sportsEventRepository.getAll();

        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<Event> eventList) throws SQLException {

        for (Event event : eventList) {

            if (event instanceof CulturalEvent culturalEvent) {

                culturalEventRepository.addNewObject(culturalEvent);

            }

            else {

                sportsEventRepository.addNewObject((SportsEvent) event);

            }

        }

    }

}