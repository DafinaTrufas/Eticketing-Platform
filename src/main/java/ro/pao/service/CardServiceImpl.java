package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.model.CardInformation;
import ro.pao.repository.CardInformationRepository;
import ro.pao.service.CardService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class CardServiceImpl implements CardService {

    private final CardInformationRepository cardInformationRepository;

    @Override
    public Optional<CardInformation> getById(UUID id) throws SQLException {

        Optional<CardInformation> cardInformation = cardInformationRepository.getObjectById(id);

        if(cardInformation.isEmpty()) {
            throw new RuntimeException("Card not found!");
        }

        return cardInformation;

    }

    @Override
    public Optional<CardInformation> getByName(String firstName, String lastName) throws SQLException {

        Optional<CardInformation> cardInformation = cardInformationRepository.getObjectByName(firstName, lastName);

        if(cardInformation.isEmpty()) {
            throw new RuntimeException("There is no card owned by this client.");
        }

        return cardInformation;

    }

    @Override
    public Map<UUID, CardInformation> getAllFromMap() {

        return listToMap(cardInformationRepository.getAll());

    }

    @Override
    public Map<UUID, CardInformation> getAllFromMapWithCondition() {

        return listToMap(cardInformationRepository.getAll().stream()
                .filter(card -> card.getBalance().equals("euro".toLowerCase()))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CardInformation> cardInformationMap) throws SQLException {

        cardInformationRepository.addAllFromGivenList(cardInformationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(CardInformation cardInformation) throws SQLException {

        cardInformationRepository.addNewObject(cardInformation);

    }

    @Override
    public void removeElementById(UUID id) {

        cardInformationRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, CardInformation newCardInformation) {

        cardInformationRepository.updateObjectById(id, newCardInformation);

    }

    @Override
    public Map<UUID, CardInformation> listToMap(List<CardInformation> cardInformationList) {

        Map<UUID, CardInformation> cardInformationMap = new HashMap<>();

        for (CardInformation cardInformation : cardInformationList) {

            cardInformationMap.put(cardInformation.getId(), cardInformation);

        }

        return cardInformationMap;

    }

}
