package ro.pao.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Event;
import ro.pao.model.abstracts.Location;
import ro.pao.model.enums.CulturalEventType;
import ro.pao.model.enums.CulturalLocationType;

import java.nio.channels.UnresolvedAddressException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CulturalEvent extends Event {

    private String title;

    private String description;

    private CulturalEventType culturalEventType;

    private CulturalLocation culturalLocation;

    public CulturalEvent(UUID id, LocalDateTime creationDateTime, LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDateTime deleteDateTime,
                         String title, String description, CulturalEventType culturalEventType, CulturalLocation culturalLocation) {
        super(id, creationDateTime, startDateTime, endDateTime, deleteDateTime);
        this.title = title;
        this.description = description;
        this.culturalEventType = culturalEventType;
        this.culturalLocation = culturalLocation;
        this.setNrAvailableTickets(culturalLocation != null ? culturalLocation.getCapacity() : 0);
    }

    public List<Integer> nrTicketsCategories() {
        Integer capacity = culturalLocation.getCapacity();
        CulturalLocationType locationType = culturalLocation.getCulturalEventLocationType();
        return List.of(capacity * locationType.getPercentageVipTickets() / 100, capacity * locationType.getPercentagePremiumTickets() / 100,
                       capacity * locationType.getPercentageMediumTickets() / 100, capacity * locationType.getPercentageLowMediumTickets() / 100);
    }

    @Override
    public String toString() {
        return "CulturalEvent{" +
                super.toString() +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", culturalEventType=" + culturalEventType +
                ", culturalLocation=" + culturalLocation +
                '}';
    }

}
