package ro.pao.repository;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.TicketMapper;
import ro.pao.model.Client;
import ro.pao.model.Ticket;
import ro.pao.model.enums.TicketType;
import ro.pao.repository.TicketRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public non-sealed class TicketRepositoryImpl implements TicketRepository {

    private static final TicketMapper ticketMapper = TicketMapper.getInstance();

    @Override
    public Optional<Ticket> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM ticket WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            Optional<Ticket> ticket = ticketMapper.mapToTicket(resultSet);

            if (ticket.isEmpty()) {

                throw new ObjectNotFoundException("Nu exista niciun bilet cu acest id!");

            }

            return ticketMapper.mapToTicket(resultSet);

        }

    }

    @Override
    public Optional<Ticket> getObjectByType (TicketType type) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM ticket WHERE type = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, type.toString().toUpperCase());

            ResultSet resultSet = stmt.executeQuery();

            List<Ticket> ticketList = ticketMapper.mapToTicketList(resultSet);

            if (ticketList.isEmpty()) {

                throw new ObjectNotFoundException("Nu exista niciun bilet cu acest id!");

            }

            return Optional.ofNullable(ticketList.get(0));

        }

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM ticket WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, Ticket newObject) {

        String updateNameSql = "UPDATE ticket SET price=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setDouble(1, newObject.getPrice());
            preparedStatement.setString(2, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (Ticket ticket) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "INSERT INTO ticket (id, price, type, event_id, client_id) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, ticket.getId().toString());
            stmt.setDouble(2, ticket.getPrice());
            stmt.setString(3, ticket.getTicketType().toString());
            stmt.setString(4, ticket.getEventId().toString());
            stmt.setString(5, ticket.getClientId().toString());
            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<Ticket> getAll() {

        String selectSql = "SELECT * FROM ticket";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return ticketMapper.mapToTicketList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<Ticket> ticketList) {

        ticketList.forEach(this::addNewObject);

    }

}
