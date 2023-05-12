package ro.pao.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Event;
import ro.pao.model.enums.TicketType;

import java.util.UUID;

@SuperBuilder
@Getter
public class Ticket {

    private UUID id;

    private UUID eventId;

    private UUID clientId;

    private Double price;

    private TicketType ticketType;

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", price='" + price + '\'' +
                ", ticketType=" + ticketType +
                '}';
    }

}
