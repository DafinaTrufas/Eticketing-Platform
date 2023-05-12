package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.pao.model.Client;
import ro.pao.model.CulturalEvent;
import ro.pao.model.MailInformation;
import ro.pao.repository.ClientRepository;
import ro.pao.service.CardService;
import ro.pao.service.ClientService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Optional<Client> getById(UUID id) throws SQLException {

        Optional<Client> client = clientRepository.getObjectById(id);

        if(client.isEmpty()) {
            throw new RuntimeException("Client not found!");
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
