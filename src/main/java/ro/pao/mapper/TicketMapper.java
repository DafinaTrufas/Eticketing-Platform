package ro.pao.mapper;

import ro.pao.model.Ticket;
import ro.pao.model.enums.CulturalLocationType;
import ro.pao.model.enums.TicketType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TicketMapper {

    private static final TicketMapper INSTANCE = new TicketMapper();

    TicketMapper() {
    }

    public static TicketMapper getInstance() {
        return INSTANCE;
    }


    public Optional<Ticket> mapToTicket(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    Ticket.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .price(resultSet.getDouble("price"))
                            .ticketType(TicketType.valueOf(resultSet.getString("type")))
                            .eventId(UUID.fromString(resultSet.getString("event_id")))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<Ticket> mapToTicketList(ResultSet resultSet) throws SQLException {

        List<Ticket> TicketList = new ArrayList<>();

        while (resultSet.next()) {

            TicketList.add(
                    Ticket.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .price(resultSet.getDouble("price"))
                            .ticketType(TicketType.valueOf(resultSet.getString("type")))
                            .eventId(UUID.fromString(resultSet.getString("event_id")))
                            .build()
            );

        }

        return TicketList;

    }

}
