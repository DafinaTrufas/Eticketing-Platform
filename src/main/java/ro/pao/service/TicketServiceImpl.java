package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.MailInformation;
import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;
import ro.pao.repository.TicketRepository;
import ro.pao.service.TicketService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private static final Logger logger = Logger.getGlobal();

    @Override
    public Optional<Ticket> getById(UUID id) throws SQLException {

        Optional<Ticket> ticket = Optional.empty();

        try {

            ticket = ticketRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return ticket;

    }

    @Override
    public Optional<Ticket> getByType(TicketType type) throws SQLException {

        Optional<Ticket> ticket = Optional.empty();

        try {

            ticket = ticketRepository.getObjectByType(type);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return ticket;

    }

    @Override
    public Map<UUID, Ticket> getAllFromMap() {

        return listToMap(ticketRepository.getAll());

    }

    @Override
    public Map<UUID, Ticket> getAllFromMapWithCondition() {

        return listToMap(ticketRepository.getAll().stream()
                .filter(ticket -> ticket.getPrice() < 80.0)
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, Ticket> ticketMap) throws SQLException {

        ticketRepository.addAllFromGivenList(ticketMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(Ticket ticket) throws SQLException {

        ticketRepository.addNewObject(ticket);

    }

    @Override
    public void removeElementById(UUID id) {

        ticketRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, Ticket newTicket) {

        ticketRepository.updateObjectById(id, newTicket);

    }

    @Override
    public Map<UUID, Ticket> listToMap(List<Ticket> ticketList) {

        Map<UUID, Ticket> ticketMap = new HashMap<>();

        for (Ticket ticket : ticketList) {

            ticketMap.put(ticket.getId(), ticket);

        }

        return ticketMap;

    }

}
