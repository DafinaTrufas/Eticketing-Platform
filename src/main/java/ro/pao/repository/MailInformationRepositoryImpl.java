package ro.pao.repository;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.MailInformationMapper;
import ro.pao.model.Client;
import ro.pao.model.MailInformation;
import ro.pao.repository.MailInformationRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public non-sealed class MailInformationRepositoryImpl implements MailInformationRepository {

    private static final MailInformationMapper mailInformationMapper = MailInformationMapper.getInstance();

    @Override
    public Optional<MailInformation> getObjectById (UUID id) throws SQLException, ObjectNotFoundException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM mail WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, id.toString());

            ResultSet resultSet = stmt.executeQuery();

            Optional<MailInformation> mailInformation = mailInformationMapper.mapToMailInformation(resultSet);

            if (mailInformation.isEmpty()) {

                throw new ObjectNotFoundException("Nu exista niciun email cu acest id!");

            }

            return mailInformationMapper.mapToMailInformation(resultSet);

        }

    }

    @Override
    public Optional<MailInformation> getObjectByAddress (String address) throws SQLException {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "SELECT * FROM mail_information WHERE address = ?";

        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, address);

            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {

                return mailInformationMapper.mapToMailInformation(resultSet);

            }

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        String updateNameSql = "DELETE FROM mail WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    @Override
    public void updateObjectById(UUID id, MailInformation newObject) {

        String updateNameSql = "UPDATE mail SET address=? WHERE id=?";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateNameSql)) {

            preparedStatement.setString(1, newObject.getAddress());
            preparedStatement.setString(2, id.toString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public void addNewObject (MailInformation mailInformation) {

        Connection connection = DatabaseConfiguration.getDatabaseConnection();
        String qrySQL = "INSERT INTO mail VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(qrySQL)) {

            stmt.setString(1, mailInformation.getId().toString());
            stmt.setString(2, mailInformation.getFirstName());
            stmt.setString(3, mailInformation.getLastName());
            stmt.setString(4, mailInformation.getAddress());
            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public List<MailInformation> getAll() {

        String selectSql = "SELECT * FROM mail";

        Connection connection = DatabaseConfiguration.getDatabaseConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            return mailInformationMapper.mapToMailInformationList(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<MailInformation> mailInformationList) {

        mailInformationList.forEach(this::addNewObject);

    }

}
