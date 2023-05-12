package ro.pao.repository;

import ro.pao.model.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {

    Optional<Client> getObjectById(UUID id) throws SQLException;

    Optional<Client> getObjectByBirthDate(LocalDate date) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, Client newObject);

    void addNewObject(Client client);

    List<Client> getAll();

    void addAllFromGivenList(List<Client> clientList);
    
}
