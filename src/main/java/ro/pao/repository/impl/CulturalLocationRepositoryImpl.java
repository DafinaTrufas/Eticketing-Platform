package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.CulturalLocationMapper;
import ro.pao.model.CulturalLocation;
import ro.pao.repository.LocationRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CulturalLocationRepositoryImpl implements LocationRepository<CulturalLocation> {

    private static final CulturalLocationMapper culturalEventMapper = CulturalLocationMapper.getInstance();

    @Override
    public Optional<CulturalLocation> getObjectById (UUID id) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM cultural_location WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return culturalEventMapper.mapToCulturalLocation(resultSet);

            }

        }

        return Optional.empty();

    }

    @Override
    public Optional<CulturalLocation> getObjectByCapacity (Integer capacity) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM cultural_location WHERE capacity = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setInt(1, capacity);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return culturalEventMapper.mapToCulturalLocationList(resultSet).stream().findAny();

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM cultural_location WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, CulturalLocation newObject) {

        String updateNameSql = "UPDATE cultural_location SET name=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getName());
            preparedStatement.setString(2, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (CulturalLocation culturalLocation) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        String qrySQLParent = "INSERT INTO location VALUES(?, ?, ?, ?)";
        String qrySQLChild = "INSERT INTO cultural_location VALUES(?, ?)";

        try (PreparedStatement stmtParent = connection.prepareStatement(qrySQLParent);
             PreparedStatement stmtChild = connection.prepareStatement(qrySQLChild)) {

            stmtParent.setString(1, culturalLocation.getId().toString());
            stmtParent.setString(2, culturalLocation.getName());
            stmtParent.setString(3, culturalLocation.getAddress());
            stmtParent.setInt(4, culturalLocation.getCapacity());

            stmtChild.setString(1, culturalLocation.getId().toString());
            stmtChild.setString(2, culturalLocation.getCulturalEventLocationType().getTypeString());

            stmtParent.executeUpdate();
            stmtChild.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<CulturalLocation> getAll() {

        String selectSql = "SELECT * FROM cultural_location";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return culturalEventMapper.mapToCulturalLocationList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<CulturalLocation> culturalLocationList) {

        culturalLocationList.forEach(this::addNewObject);

    }

}
