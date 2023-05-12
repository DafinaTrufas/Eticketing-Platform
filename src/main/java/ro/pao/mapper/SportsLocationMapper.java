package ro.pao.mapper;

import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsLocation;
import ro.pao.model.enums.CulturalLocationType;
import ro.pao.model.enums.SportsLocationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SportsLocationMapper {

    private static final SportsLocationMapper INSTANCE = new SportsLocationMapper();

    SportsLocationMapper() {
    }

    public static SportsLocationMapper getInstance() {
        return INSTANCE;
    }


    public Optional<SportsLocation> mapToSportsLocation(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    SportsLocation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .sportsLocationType(SportsLocationType.valueOf(String.join("_", resultSet.getString("type").toUpperCase().split(" "))))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<SportsLocation> mapToSportsLocationList(ResultSet resultSet) throws SQLException {

        List<SportsLocation> sportsLocationList = new ArrayList<>();

        while (resultSet.next()) {

            sportsLocationList.add(
                    SportsLocation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .sportsLocationType(SportsLocationType.valueOf(String.join("_", resultSet.getString("type").toUpperCase().split(" "))))
                            .build()
            );

        }

        return sportsLocationList;

    }

}
