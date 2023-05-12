package ro.pao.repository;

import ro.pao.model.MailInformation;
import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository {

    Optional<Ticket> getObjectById(UUID id) throws SQLException;

    Optional<Ticket> getObjectByType(TicketType type) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, Ticket newObject);

    void addNewObject(Ticket Ticket);

    List<Ticket> getAll();

    void addAllFromGivenList(List<Ticket> TicketList);
    
}
