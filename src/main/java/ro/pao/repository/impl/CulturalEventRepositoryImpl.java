package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.CulturalEventMapper;
import ro.pao.mapper.EventMapper;
import ro.pao.model.CulturalEvent;
import ro.pao.repository.EventRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CulturalEventRepositoryImpl implements EventRepository<CulturalEvent> {

    private static final EventMapper eventMapper = EventMapper.getInstance();
    private static final CulturalEventMapper culturalEventMapper = CulturalEventMapper.getInstance();

    @Override
    public Optional<CulturalEvent> getObjectById (UUID id) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM cultural_event WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return culturalEventMapper.mapToCulturalEvent(resultSet);

            }

        }

        return Optional.empty();

    }

    @Override
    public Optional<CulturalEvent> getObjectByLocation (UUID id) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM cultural_event WHERE location_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return culturalEventMapper.mapToCulturalEventList(resultSet).stream().findAny();

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM cultural_event WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, CulturalEvent newObject) {

        String updateNameSql = "UPDATE cultural_event SET title=?, description=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getTitle());
            preparedStatement.setString(2, newObject.getDescription());
            preparedStatement.setString(3, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (CulturalEvent culturalEvent) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        String qrySQLParent = "INSERT INTO event (id, creationDate) VALUES(?, ?)";
        String qrySQLChild = "INSERT INTO cultural_event VALUES(?, ?, ?)";

        LocalDateTime startDate = LocalDateTime.now();
        java.util.Date startUtilDate = java.util.Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant());
        java.sql.Date startSqlDate = new java.sql.Date(startUtilDate.getTime());

        try (PreparedStatement stmtParent = connection.prepareStatement(qrySQLParent);
             PreparedStatement stmtChild = connection.prepareStatement(qrySQLChild)) {

            stmtParent.setString(1, culturalEvent.getId().toString());
            stmtParent.setDate(2, startSqlDate);

            stmtChild.setString(1, culturalEvent.getId().toString());
            stmtChild.setString(2, culturalEvent.getTitle());
            stmtChild.setString(3, culturalEvent.getDescription());

            stmtParent.executeUpdate();
            stmtChild.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<CulturalEvent> getAll() {

        String selectSql = "SELECT * FROM cultural_event";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return culturalEventMapper.mapToCulturalEventList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<CulturalEvent> culturalEventList) {

        culturalEventList.forEach(this::addNewObject);

    }

}
