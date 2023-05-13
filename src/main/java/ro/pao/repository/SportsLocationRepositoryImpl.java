package ro.pao.repository;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.SportsLocationMapper;
import ro.pao.model.Client;
import ro.pao.model.SportsEvent;
import ro.pao.model.SportsLocation;
import ro.pao.repository.LocationRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public non-sealed class SportsLocationRepositoryImpl implements LocationRepository<SportsLocation> {

    private static final SportsLocationMapper sportsLocationMapper = SportsLocationMapper.getInstance();

    @Override
    public Optional<SportsLocation> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM sports_location WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            Optional<SportsLocation> sportsLocation = sportsLocationMapper.mapToSportsLocation(resultSet);

            if (sportsLocation.isEmpty()) {

                throw new ObjectNotFoundException("Nu exista nicio locatie sportiva cu acest id!");

            }

            return sportsLocationMapper.mapToSportsLocation(resultSet);

        }

    }

    @Override
    public Optional<SportsLocation> getObjectByCapacity (Integer capacity) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM sports_location WHERE capacity = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setInt(1, capacity);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return sportsLocationMapper.mapToSportsLocationList(resultSet).stream().findAny();

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM sports_location WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, SportsLocation newObject) {

        String updateNameSql = "UPDATE sports_location SET name=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getName());
            preparedStatement.setString(2, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (SportsLocation sportsLocation) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        String qrySQLParent = "INSERT INTO location VALUES(?, ?, ?, ?)";
        String qrySQLChild = "INSERT INTO sports_location VALUES(?, ?)";

        try (PreparedStatement stmtParent = connection.prepareStatement(qrySQLParent);
             PreparedStatement stmtChild = connection.prepareStatement(qrySQLChild)) {

            stmtParent.setString(1, sportsLocation.getId().toString());
            stmtParent.setString(2, sportsLocation.getName());
            stmtParent.setString(3, sportsLocation.getAddress());
            stmtParent.setInt(4, sportsLocation.getCapacity());

            stmtChild.setString(1, sportsLocation.getId().toString());
            stmtChild.setString(2, sportsLocation.getSportsLocationType().getTypeString());

            stmtParent.executeUpdate();
            stmtChild.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<SportsLocation> getAll() {

        String selectSql = "SELECT * FROM sports_location";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return sportsLocationMapper.mapToSportsLocationList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<SportsLocation> SportsLocationList) {

        SportsLocationList.forEach(this::addNewObject);

    }

}
