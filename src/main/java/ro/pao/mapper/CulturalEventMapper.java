package ro.pao.mapper;

import ro.pao.model.CulturalEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CulturalEventMapper {

    private static final CulturalEventMapper INSTANCE = new CulturalEventMapper();

    CulturalEventMapper() {
    }

    public static CulturalEventMapper getInstance() {
        return INSTANCE;
    }


    public Optional<CulturalEvent> mapToCulturalEvent(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    CulturalEvent.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<CulturalEvent> mapToCulturalEventList(ResultSet resultSet) throws SQLException {

        List<CulturalEvent> culturalEventList = new ArrayList<>();

        while (resultSet.next()) {

            culturalEventList.add(
                    CulturalEvent.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .build()
            );

        }

        return culturalEventList;

    }

}
