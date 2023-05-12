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

public interface TicketService extends Service<Ticket> {

    Optional<Ticket> getByType(TicketType type) throws SQLException;

}
