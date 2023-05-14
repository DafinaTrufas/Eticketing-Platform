package ro.pao.gateways;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ro.pao.mapper.HolidayMapper;
import ro.pao.model.CulturalEvent;
import ro.pao.model.Holiday;
import ro.pao.model.abstracts.Event;
import ro.pao.repository.CulturalEventRepositoryImpl;
import ro.pao.repository.SportsEventRepositoryImpl;
import ro.pao.service.CulturalEventServiceImpl;
import ro.pao.service.EventService;
import ro.pao.service.EventServiceImpl;
import ro.pao.service.SportsEventServiceImpl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class Requests {

    private static HttpClient httpClient = HttpClient.newHttpClient();

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static HolidayMapper holidayMapper = HolidayMapper.getInstance();

    private final EventService<Event> eventService = new EventServiceImpl(new CulturalEventServiceImpl(new CulturalEventRepositoryImpl()), new SportsEventServiceImpl(new SportsEventRepositoryImpl()));

    private final EventService<CulturalEvent> culturalEventService = new CulturalEventServiceImpl(new CulturalEventRepositoryImpl());

    public void saveRequestInfo() {

        try {

            HttpRequest httpRequestHolidays = HttpRequest.newBuilder()
                    .uri(new URI("https://holidays.abstractapi.com/v1/?api_key=335f82d1890d44c0bf17bdfd1a039121&country=US&year=2020&month=12"))
                    .GET()
                    .build();

            var httpResponseHolidays = httpClient.send(httpRequestHolidays, HttpResponse.BodyHandlers.ofString());

            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); var body = httpResponseHolidays.body();

            JsonNode holiday = objectMapper.readTree(body);

            holiday.forEach(holidayObject -> { if (holidayMapper.mapToHolidayObject(holidayObject).getName().contains("Christmas"))
                                                    {
                                                        try {
                                                            eventService.addOnlyOne(CulturalEvent.builder()
                                                                    .id(UUID.randomUUID())
                                                                    .title(holidayMapper.mapToHolidayObject(holidayObject).getName() + " concert")
                                                                    .description("Concert's description: " + holidayMapper.mapToHolidayObject(holidayObject).getDescription())
                                                                    .build()
                                                            );
                                                        } catch (SQLException e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }});

            System.out.println("\nEvenimentele culturale, dupa adaugarea celor desfasurate cu ocazia sarbatorilor din luna decembrie:");
            culturalEventService.getAllFromMap().entrySet().stream().forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
