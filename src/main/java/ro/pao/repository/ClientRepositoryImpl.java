package ro.pao.repository;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.ClientMapper;
import ro.pao.model.Client;
import ro.pao.repository.ClientRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public non-sealed class ClientRepositoryImpl implements ClientRepository {

    private static final ClientMapper clientMapper = ClientMapper.getInstance();

    @Override
    public Optional<Client> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM client WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            Optional<Client> client = clientMapper.mapToClient(resultSet);

            if (client.isEmpty()) {

                throw new ObjectNotFoundException("Nu exista niciun client cu acest id!");

            }

            return clientMapper.mapToClient(resultSet);

        }

    }

    @Override
    public Optional<Client> getObjectByBirthDate (LocalDate date) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM client WHERE birthDate = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setDate(1, Date.valueOf(date));

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return clientMapper.mapToClientList(resultSet).stream().findAny();

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM client WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, Client newObject) {

        String updateNameSql = "UPDATE client SET firstname=?, lastname=?, address=?, birthdate=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        java.util.Date birthUtilDate = java.util.Date.from(newObject.getBirthDate().atTime(LocalTime.MIDNIGHT).atZone(ZoneId.systemDefault()).toInstant());
        java.sql.Date birthSqlDate = new java.sql.Date(birthUtilDate.getTime());

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getFirstName());
            preparedStatement.setString(2, newObject.getLastName());
            preparedStatement.setString(3, newObject.getAddress());
            preparedStatement.setDate(4, birthSqlDate);
            preparedStatement.setString(5, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (Client client) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "INSERT INTO client VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, client.getId().toString());
            stmt.setString(2, client.getFirstName());
            stmt.setString(3, client.getLastName());
            stmt.setString(4, client.getAddress());
            stmt.setDate(5, Date.valueOf(client.getBirthDate()));
            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<Client> getAll() {

        String selectSql = "SELECT * FROM client";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return clientMapper.mapToClientList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<Client> clientList) {

        clientList.forEach(this::addNewObject);

    }

}
