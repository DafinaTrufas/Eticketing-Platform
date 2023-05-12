package ro.pao.mapper;

import ro.pao.model.Client;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientMapper {

    private static final ClientMapper INSTANCE = new ClientMapper();

    ClientMapper() {
    }

    public static ClientMapper getInstance() {
        return INSTANCE;
    }


    public Optional<Client> mapToClient(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    Client.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .firstName(resultSet.getString("firstname"))
                            .lastName(resultSet.getString("lastname"))
                            .address(resultSet.getString("address"))
                            .birthDate(resultSet.getDate("birthdate").toLocalDate())
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<Client> mapToClientList(ResultSet resultSet) throws SQLException {

        List<Client> clientList = new ArrayList<>();

        while (resultSet.next()) {

            clientList.add(
                    Client.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .firstName(resultSet.getString("firstname"))
                            .lastName(resultSet.getString("lastname"))
                            .address(resultSet.getString("address"))
                            .birthDate(resultSet.getDate("birthdate").toLocalDate())
                            .build()
            );

        }

        return clientList;

    }

}
