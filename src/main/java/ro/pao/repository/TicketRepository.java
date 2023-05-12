package ro.pao.repository;

import ro.pao.model.MailInformation;
import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;

import java.io.Serial;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepository extends Repository<Ticket> {

    Optional<Ticket> getObjectByType(TicketType type) throws SQLException;
    
}
