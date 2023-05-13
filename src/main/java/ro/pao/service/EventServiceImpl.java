package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.model.CulturalEvent;
import ro.pao.model.SportsEvent;
import ro.pao.model.abstracts.Event;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public non-sealed class EventServiceImpl implements EventService<Event> {

    private final EventService<CulturalEvent> culturalEventService;

    private final EventService<SportsEvent> sportsEventService;

    @Override
    public Optional<Event> getById(UUID id) {

        try {

            return culturalEventService.getById(id).map(Function.identity());

        } catch(RuntimeException ec) {

            try {

                return sportsEventService.getById(id).map(Function.identity());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Optional<Event> getByLocation(UUID id) {

        try {

            return culturalEventService.getByLocation(id).map(Function.identity());

        } catch(RuntimeException ec) {

            try {

                return sportsEventService.getByLocation(id).map(Function.identity());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Map<UUID, Event> getAllFromMap() {

        Map<UUID, Event> eventMap = new HashMap<>();

        eventMap.putAll(culturalEventService.getAllFromMap());
        eventMap.putAll(sportsEventService.getAllFromMap());

        return eventMap;

    }

    @Override
    public Map<UUID, Event> getAllFromMapWithCondition() {

        Map<UUID, Event> eventMap = new HashMap<>();

        eventMap.putAll(culturalEventService.getAllFromMapWithCondition());
        eventMap.putAll(sportsEventService.getAllFromMapWithCondition());

        return eventMap;

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, Event> eventMap) throws SQLException {

        for (Event event : eventMap.values()) {

            addOnlyOne(event);

        }

    }

    @Override
    public void addOnlyOne(Event event) throws SQLException {

        if (event instanceof CulturalEvent culturalEvent) {

            culturalEventService.addOnlyOne(culturalEvent);

        }

        else {

            sportsEventService.addOnlyOne((SportsEvent) event);

        }

    }

    @Override
    public void removeElementById(UUID id) {

        try {

            culturalEventService.removeElementById(id);

        } catch(RuntimeException ec) {

            sportsEventService.removeElementById(id);

        }

    }

    @Override
    public void updateElementById(UUID id, Event event) {

        if (event instanceof CulturalEvent culturalEvent) {

            culturalEventService.updateElementById(id, culturalEvent);

        }

        else {

            sportsEventService.updateElementById(id, (SportsEvent) event);

        }

    }

    @Override
    public Map<UUID, Event> listToMap(List<Event> eventList) {

        Map<UUID, Event> eventMap = new HashMap<>();

        Iterator<Event> eventIterator = eventList.iterator();

        while (eventIterator.hasNext()) {

            Event event = eventIterator.next();

            eventMap.put(event.getId(), event);

        }

        return eventMap;

    }

}
