package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;
import ro.pao.repository.TicketRepository;
import ro.pao.service.TicketService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public Optional<Ticket> getById(UUID id) throws SQLException {

        Optional<Ticket> ticket = ticketRepository.getObjectById(id);

        if(ticket.isEmpty()) {
            throw new RuntimeException("Ticket not found!");
        }

        return ticket;

    }

    @Override
    public Optional<Ticket> getByType(TicketType type) throws SQLException {

        Optional<Ticket> ticket = ticketRepository.getObjectByType(type);

        if(ticket.isEmpty()) {
            throw new RuntimeException("There is no Ticket of this type.");
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
