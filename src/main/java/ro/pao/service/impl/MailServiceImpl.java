package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import ro.pao.model.MailInformation;
import ro.pao.repository.MailInformationRepository;
import ro.pao.service.CardService;
import ro.pao.service.MailService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final MailInformationRepository mailInformationRepository;

    @Override
    public Optional<MailInformation> getById(UUID id) throws SQLException {

        Optional<MailInformation> mailInformation = mailInformationRepository.getObjectById(id);

        if(mailInformation.isEmpty()) {
            throw new RuntimeException("MailInformation not found!");
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
    public void addAllFromGivenMap(Map<UUID, MailInformation> mailInformationMap) {

        mailInformationRepository.addAllFromGivenList(mailInformationMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(MailInformation mailInformation) {

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

        for (MailInformation mailInformation : mailInformationList) {

            mailInformationMap.put(mailInformation.getId(), mailInformation);

        }

        return mailInformationMap;

    }

}
