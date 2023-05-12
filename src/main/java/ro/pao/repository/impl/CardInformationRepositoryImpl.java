package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.CardInformationMapper;
import ro.pao.model.CardInformation;
import ro.pao.model.Client;
import ro.pao.repository.CardInformationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CardInformationRepositoryImpl implements CardInformationRepository {

    private static final CardInformationMapper cardInformationMapper = CardInformationMapper.getInstance();

    @Override
    public Optional<CardInformation> getObjectById (UUID id) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM card WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return cardInformationMapper.mapToCardInformation(resultSet);

            }

        }

        return Optional.empty();

    }

    @Override
    public Optional<CardInformation> getObjectByName (String firstName, String lastName) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM client WHERE firstName = ? AND lastname = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, firstName);
            stmt.setString(2, lastName);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return cardInformationMapper.mapToCardInformationList(resultSet).stream().findAny();

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM card WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, CardInformation newObject) {

        String updateNameSql = "UPDATE card SET balance=?, lastname=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getBalance());
            preparedStatement.setString(2, newObject.getLastNameOwner());
            preparedStatement.setString(3, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (CardInformation cardInformation) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "INSERT INTO card VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, cardInformation.getBalance());
            stmt.setString(2, cardInformation.getFirstNameOwner());
            stmt.setString(3, cardInformation.getLastNameOwner());
            stmt.setString(4, cardInformation.getCardNumber());
            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<CardInformation> getAll() {

        String selectSql = "SELECT * FROM card";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return cardInformationMapper.mapToCardInformationList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<CardInformation> cardInformationList) {

        cardInformationList.forEach(this::addNewObject);

    }

}
