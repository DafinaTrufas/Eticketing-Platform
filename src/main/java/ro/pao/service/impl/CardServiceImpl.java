package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.pao.model.CardInformation;
import ro.pao.model.CulturalEvent;
import ro.pao.model.MailInformation;
import ro.pao.repository.CardInformationRepository;
import ro.pao.service.CardService;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardInformationRepository cardInformationRepository;

    @Override
    public Optional<CardInformation> getByCardNumber(String cardNumber) throws SQLException {

        Optional<CardInformation> cardInformation = cardInformationRepository.getObjectByCardNumber(cardNumber);

        if(cardInformation.isEmpty()) {
            throw new RuntimeException("Card not found!");
        }

        return cardInformation;

    }

    @Override
    public Map<String, CardInformation> getAllFromMap() {

        return listToMap(cardInformationRepository.getAll());

    }

    @Override
    public void addAllFromGivenMap(Map<String, CardInformation> cardInformationMap) {

        cardInformationRepository.addAllFromGivenList(cardInformationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(CardInformation cardInformation) {

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
    public Map<String, CardInformation> listToMap(List<CardInformation> cardInformationList) {

        Map<String, CardInformation> cardInformationMap = new HashMap<>();

        for (CardInformation cardInformation : cardInformationList) {

            cardInformationMap.put(cardInformation.getCardNumber(), cardInformation);

        }

        return cardInformationMap;

    }

}
