package ro.pao.mapper;

import ro.pao.model.CulturalLocation;
import ro.pao.model.enums.CulturalLocationType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CulturalLocationMapper {

    private static final CulturalLocationMapper INSTANCE = new CulturalLocationMapper();

    CulturalLocationMapper() {
    }

    public static CulturalLocationMapper getInstance() {
        return INSTANCE;
    }


    public Optional<CulturalLocation> mapToCulturalLocation(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    CulturalLocation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .culturalEventLocationType(CulturalLocationType.valueOf(resultSet.getString("type")))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<CulturalLocation> mapToCulturalLocationList(ResultSet resultSet) throws SQLException {

        List<CulturalLocation> culturalLocationList = new ArrayList<>();

        while (resultSet.next()) {

            culturalLocationList.add(
                    CulturalLocation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .culturalEventLocationType(CulturalLocationType.valueOf(String.join("_", resultSet.getString("type").toUpperCase().split(" "))))
                            .build()
            );

        }

        return culturalLocationList;

    }

}
