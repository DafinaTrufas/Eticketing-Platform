package ro.pao.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import ro.pao.model.Holiday;

public class HolidayMapper {

    private static final HolidayMapper INSTANCE = new HolidayMapper();

    private HolidayMapper() {
    }

    public static HolidayMapper getInstance() {

        return INSTANCE;

    }

    public Holiday mapToHolidayObject(JsonNode jsonNode) {

        return Holiday.builder()
                .name(jsonNode.get("name").asText())
                .name_local(jsonNode.get("name_local").asText())
                .country(jsonNode.get("country").asText())
                .location(jsonNode.get("location").asText())
                .type(jsonNode.get("type").asText())
                .date(jsonNode.get("date").asText())
                .date_year(jsonNode.get("date_year").asText())
                .date_month(jsonNode.get("date_month").asText())
                .date_day(jsonNode.get("date_day").asText())
                .build();

    }

}
