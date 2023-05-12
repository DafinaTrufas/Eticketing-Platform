package ro.pao.mapper;

import ro.pao.model.CulturalEvent;
import ro.pao.model.SportsEvent;
import ro.pao.model.abstracts.Event;
import ro.pao.model.enums.CulturalEventType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class EventMapper {

    private static final EventMapper INSTANCE = new EventMapper();

    private static final CulturalEventMapper INSTANCE1 = new CulturalEventMapper();

    private static final SportsEventMapper INSTANCE2 = new SportsEventMapper();

    private EventMapper() {
    }

    public static EventMapper getInstance() {
        return INSTANCE;
    }

    public Optional<Event> mapToEvent(ResultSet resultSet) throws SQLException {

        try {

            if (INSTANCE1.mapToCulturalEvent(resultSet).isPresent()) {

                return Optional.of(INSTANCE1.mapToCulturalEvent(resultSet).get().builder()
                        .creationDateTime(resultSet.getDate("creationDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .startDateTime(resultSet.getDate("startDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .endDateTime(resultSet.getDate("endDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .deleteDateTime(resultSet.getDate("deleteDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .nrAvailableTickets(resultSet.getInt("nrAvailableTickets"))
                        .build());

            }

        } catch (RuntimeException e) {

            if (INSTANCE2.mapToSportsEvent(resultSet).isPresent()) {

                return Optional.of(INSTANCE2.mapToSportsEvent(resultSet).get().builder()
                        .creationDateTime(resultSet.getDate("creationDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .startDateTime(resultSet.getDate("startDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .endDateTime(resultSet.getDate("endDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .deleteDateTime(resultSet.getDate("deleteDate").toLocalDate().atTime(LocalTime.MIDNIGHT))
                        .nrAvailableTickets(resultSet.getInt("nrAvailableTickets"))
                        .build());

            }

        }

        return Optional.empty();

    }

    public List<Event> mapToEventList(ResultSet resultSet) throws SQLException {

        List<Event> eventList = new ArrayList<>();

        while (resultSet.next()) {

            eventList.add(mapToEvent(resultSet).get());

        }

        return eventList;

    }

}
