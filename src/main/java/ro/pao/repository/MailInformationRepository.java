package ro.pao.repository;

import ro.pao.model.CulturalLocation;
import ro.pao.model.MailInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MailInformationRepository {

    Optional<MailInformation> getObjectById(UUID id) throws SQLException;

    Optional<MailInformation> getObjectByAddress(String address) throws SQLException;

    void deleteObjectById(UUID id);

    void updateObjectById(UUID id, MailInformation newObject);

    void addNewObject(MailInformation MailInformation);

    List<MailInformation> getAll();

    void addAllFromGivenList(List<MailInformation> MailInformationList);
    
}
