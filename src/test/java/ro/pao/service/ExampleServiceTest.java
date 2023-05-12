package ro.pao.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ro.pao.application.Menu;
import ro.pao.model.CulturalEvent;

import java.sql.SQLException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RequiredArgsConstructor
class CulturalEventServiceTest {

    private final Menu menu = Menu.getInstance();
    private final CulturalEventService culturalEventService;

    @Test
    void whenGivenCulturalEventClass_thenElementIsAdd() throws SQLException {

        CulturalEvent culturalEvent = CulturalEvent.builder()
                .id(UUID.randomUUID())
                .build();

        culturalEventService.addOnlyOne(culturalEvent);

        assertEquals(1, culturalEventService.getAllFromMap().size());

    }
}
