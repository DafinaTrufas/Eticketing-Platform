package ro.pao.service;

import ro.pao.model.MailInformation;
import ro.pao.model.SportsLocation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MailService {

    Optional<MailInformation> getById(UUID id) throws SQLException;

    Optional<MailInformation> getByAddress(String address) throws SQLException;

    Map<UUID, MailInformation> getAllFromMap();

    Map<UUID, MailInformation> getAllFromMapWithCondition();

    void addAllFromGivenMap(Map<UUID, MailInformation> mailInformationMap);

    void addOnlyOne(MailInformation mailInformation);

    void removeElementById(UUID id);

    void updateElementById(UUID id, MailInformation newMail);

    Map<UUID, MailInformation> listToMap(List<MailInformation> mailInformationList);

}
