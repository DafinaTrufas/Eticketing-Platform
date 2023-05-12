package ro.pao.service;

import ro.pao.model.Client;
import ro.pao.model.MailInformation;
import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface TicketService {

    Optional<Ticket> getById(UUID id) throws SQLException;

    Optional<Ticket> getByType(TicketType type) throws SQLException;

    Map<UUID, Ticket> getAllFromMap();

    Map<UUID, Ticket> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, Ticket> ticketMap);

    void addOnlyOne(Ticket ticket);

    void removeElementById(UUID id);

    void updateElementById(UUID id, Ticket newTicket);

    Map<UUID, Ticket> listToMap(List<Ticket> ticketList);

}
