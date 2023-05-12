package ro.pao.service;

import ro.pao.model.CardInformation;
import ro.pao.model.Client;
import ro.pao.model.MailInformation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ClientService {

    Optional<Client> getById(UUID id) throws SQLException;

    Optional<Client> getByBirthDate(LocalDate date) throws SQLException;

    Map<UUID, Client> getAllFromMap();

    Map<UUID, Client> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, Client> clientMap);

    void addOnlyOne(Client client);

    void removeElementById(UUID id);

    void updateElementById(UUID id, Client newClient);

    Map<UUID, Client> listToMap(List<Client> clientList);

}
