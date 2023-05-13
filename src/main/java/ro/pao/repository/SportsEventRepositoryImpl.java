package ro.pao.repository;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.SportsEventMapper;
import ro.pao.model.Client;
import ro.pao.model.SportsEvent;
import ro.pao.repository.EventRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public non-sealed class SportsEventRepositoryImpl implements EventRepository<SportsEvent> {

    private static final SportsEventMapper sportsEventMapper = SportsEventMapper.getInstance();

    @Override
    public Optional<SportsEvent> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM sports_event WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            Optional<SportsEvent> sportsEvent = sportsEventMapper.mapToSportsEvent(resultSet);

            if (sportsEvent.isEmpty()) {

                throw new ObjectNotFoundException("Nu exista niciun eveniment sportiv cu acest id!");

            }

            return sportsEventMapper.mapToSportsEvent(resultSet);

        }

    }

    @Override
    public Optional<SportsEvent> getObjectByLocation (UUID id) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM sports_event WHERE location_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return sportsEventMapper.mapToSportsEventList(resultSet).stream().findAny();

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM sports_event WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, SportsEvent newObject) {

        String updateNameSql = "UPDATE sports_event SET competition=?, stage=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getCompetition());
            preparedStatement.setString(2, newObject.getStage());
            preparedStatement.setString(3, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (SportsEvent sportsEvent) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        String qrySQLParent = "INSERT INTO event (id, creationDate) VALUES(?, ?)";
        String qrySQLChild = "INSERT INTO sports_event VALUES(?, ?, ?)";

        LocalDateTime creationDate = LocalDateTime.now();
        java.util.Date creationUtilDate = Date.from(creationDate.atZone(ZoneId.systemDefault()).toInstant());
        java.sql.Date creationSqlDate = new java.sql.Date(creationUtilDate.getTime());

        try (PreparedStatement stmtParent = connection.prepareStatement(qrySQLParent);
             PreparedStatement stmtChild = connection.prepareStatement(qrySQLChild)) {

            stmtParent.setString(1, sportsEvent.getId().toString());
            stmtParent.setDate(2, creationSqlDate);

            stmtChild.setString(1, sportsEvent.getId().toString());
            stmtChild.setString(2, sportsEvent.getCompetition());
            stmtChild.setString(3, sportsEvent.getStage());

            stmtParent.executeUpdate();
            stmtChild.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<SportsEvent> getAll() {

        String selectSql = "SELECT * FROM sports_event";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return sportsEventMapper.mapToSportsEventList(resultSet);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<SportsEvent> sportsEventList) {

        sportsEventList.forEach(this::addNewObject);

    }

}
