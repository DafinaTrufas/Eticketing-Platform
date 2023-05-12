package ro.pao.service;

import ro.pao.model.CardInformation;
import ro.pao.model.MailInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CardService {

    Optional<CardInformation> getByCardNumber(String cardNumber) throws SQLException;

    Map<String, CardInformation> getAllFromMap();

    void addAllFromGivenMap(Map<String, CardInformation> cardInformationMap);

    void addOnlyOne(CardInformation cardInformation);

    void removeElementById(UUID id);

    void updateElementById(UUID id, CardInformation newCard);

    Map<String, CardInformation> listToMap(List<CardInformation> cardInformationList);

}
