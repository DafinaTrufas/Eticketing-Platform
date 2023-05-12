package ro.pao.mapper;

import ro.pao.model.CulturalEvent;
import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsEvent;
import ro.pao.model.abstracts.Event;
import ro.pao.model.abstracts.Location;
import ro.pao.model.enums.CulturalEventType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LocationMapper {

    private static final LocationMapper INSTANCE = new LocationMapper();

    private static final CulturalLocationMapper INSTANCE1 = new CulturalLocationMapper();

    private static final SportsLocationMapper INSTANCE2 = new SportsLocationMapper();

    private LocationMapper() {
    }

    public static LocationMapper getInstance() {
        return INSTANCE;
    }


    public Optional<Location> mapToLocation(ResultSet resultSet) throws SQLException {

        try {

            if (INSTANCE1.mapToCulturalLocation(resultSet).isPresent()) {

                return Optional.of(INSTANCE1.mapToCulturalLocation(resultSet).get().builder()
                                    .id(UUID.randomUUID())
                                    .name(resultSet.getString("name"))
                                    .address(resultSet.getString("address"))
                                    .build());

            }

        } catch(RuntimeException e1) {

            return Optional.of(INSTANCE2.mapToSportsLocation(resultSet).get().builder()
                                .id(UUID.randomUUID())
                                .name(resultSet.getString("name"))
                                .address(resultSet.getString("address"))
                                .build());

        }

        return Optional.empty();

    }

    public List<Location> mapToLocationList(ResultSet resultSet) throws SQLException {

        List<Location> locationList = new ArrayList<>();

        while (resultSet.next()) {

            locationList.add(mapToLocation(resultSet).get());

        }

        return locationList;

    }

}
