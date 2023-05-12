package ro.pao.mapper;

import ro.pao.model.CulturalEvent;
import ro.pao.model.SportsEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SportsEventMapper {

    private static final SportsEventMapper INSTANCE = new SportsEventMapper();

    SportsEventMapper() {
    }

    public static SportsEventMapper getInstance() {
        return INSTANCE;
    }


    public Optional<SportsEvent> mapToSportsEvent(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    SportsEvent.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .competition(resultSet.getString("competition"))
                            .stage(resultSet.getString("stage"))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<SportsEvent> mapToSportsEventList(ResultSet resultSet) throws SQLException {

        List<SportsEvent> sportsEventList = new ArrayList<>();

        while (resultSet.next()) {

            sportsEventList.add(
                    SportsEvent.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .competition(resultSet.getString("competition"))
                            .stage(resultSet.getString("stage"))
                            .build()
            );

        }

        return sportsEventList;

    }

}
