package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Client;
import ro.pao.repository.ClientRepository;
import ro.pao.service.ClientService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    private static final Logger logger = Logger.getGlobal();

    @Override
    public Optional<Client> getById(UUID id) throws SQLException {

        Optional<Client> client = Optional.empty();

        try {

            client = clientRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return client;

    }

    @Override
    public Optional<Client> getByBirthDate(LocalDate date) throws SQLException {

        Optional<Client> client = clientRepository.getObjectByBirthDate(date);

        if(client.isEmpty()) {
            throw new RuntimeException("There is no client born on the given date.");
        }

        return client;

    }

    @Override
    public Map<UUID, Client> getAllFromMap() {

        return listToMap(clientRepository.getAll());

    }

    @Override
    public Map<UUID, Client> getAllFromMapWithCondition() {

        return listToMap(clientRepository.getAll().stream()
                .filter(client -> client.getLastName().contains("escu"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, Client> clientMap) throws SQLException {

        clientRepository.addAllFromGivenList(clientMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(Client client) throws SQLException {

        clientRepository.addNewObject(client);

    }

    @Override
    public void removeElementById(UUID id) {

        clientRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, Client newClient) {

        clientRepository.updateObjectById(id, newClient);

    }

    @Override
    public Map<UUID, Client> listToMap(List<Client> clientList) {

        Map<UUID, Client> clientMap = new HashMap<>();

        for (Client client : clientList) {

            clientMap.put(client.getId(), client);

        }

        return clientMap;

    }

}
