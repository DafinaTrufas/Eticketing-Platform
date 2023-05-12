package ro.pao.repository;

import ro.pao.model.CardInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardInformationRepository {

    Optional<CardInformation> getObjectByCardNumber(String cardNumber) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, CardInformation newObject);

    void addNewObject(CardInformation cardInformation);

    List<CardInformation> getAll();

    void addAllFromGivenList(List<CardInformation> cardInformationList);
    
}
