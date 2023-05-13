package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.MailInformation;
import ro.pao.repository.MailInformationRepository;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public non-sealed class MailServiceImpl implements MailService {

    private final MailInformationRepository mailInformationRepository;

    private static final Logger logger = Logger.getGlobal();

    @Override
    public Optional<MailInformation> getById(UUID id) throws SQLException {

        Optional<MailInformation> mailInformation = Optional.empty();

        try {

            mailInformation = mailInformationRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return mailInformation;

    }

    @Override
    public Optional<MailInformation> getByAddress(String address) throws SQLException {

        Optional<MailInformation> mailInformation = mailInformationRepository.getObjectByAddress(address);

        if(mailInformation.isEmpty()) {
            throw new RuntimeException("There is no MailInformation with this address.");
        }

        return mailInformation;

    }

    @Override
    public Map<UUID, MailInformation> getAllFromMap() {

        return listToMap(mailInformationRepository.getAll());

    }

    @Override
    public Map<UUID, MailInformation> getAllFromMapWithCondition() {

        return listToMap(mailInformationRepository.getAll().stream()
                .filter(mailInformation -> mailInformation.getAddress().contains("gmail"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, MailInformation> mailInformationMap) throws SQLException {

        mailInformationRepository.addAllFromGivenList(mailInformationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(MailInformation mailInformation) throws SQLException {

        mailInformationRepository.addNewObject(mailInformation);

    }

    @Override
    public void removeElementById(UUID id) {

        mailInformationRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, MailInformation newMailInformation) {

        mailInformationRepository.updateObjectById(id, newMailInformation);

    }

    @Override
    public Map<UUID, MailInformation> listToMap(List<MailInformation> mailInformationList) {

        Map<UUID, MailInformation> mailInformationMap = new HashMap<>();

        Iterator<MailInformation> mailInformationIterator = mailInformationList.iterator();

        while (mailInformationIterator.hasNext()) {

            MailInformation mailInformation = mailInformationIterator.next();

            mailInformationMap.put(mailInformation.getId(), mailInformation);

        }

        return mailInformationMap;

    }

}
