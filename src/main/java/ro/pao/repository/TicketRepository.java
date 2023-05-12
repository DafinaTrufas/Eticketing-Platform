package ro.pao.repository;

import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface TicketRepository extends Repository<Ticket> permits TicketRepositoryImpl {

    Optional<Ticket> getObjectByType(TicketType type) throws SQLException;
    
}
