package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.application.csv.CSVFormatter;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.CardInformation;
import ro.pao.repository.CardInformationRepository;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static ro.pao.application.utils.Constants.CSV_PATH_WRITE;

@RequiredArgsConstructor
public non-sealed class CardServiceImpl implements CardService {

    private final CardInformationRepository cardInformationRepository;

    private static final Logger logger = Logger.getGlobal();

    FileHandler fileHandler;

    File file = new File(CSV_PATH_WRITE);

    {
        try {

            if (!file.exists()) {

                file.getParentFile().mkdirs();

                file.createNewFile();

            }

            fileHandler = new FileHandler(file.getAbsolutePath());
            fileHandler.setFormatter(new CSVFormatter());
            logger.addHandler(fileHandler);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    @Override
    public Optional<CardInformation> getById(UUID id) throws SQLException {

        Optional<CardInformation> card = Optional.empty();

        try {

            card = cardInformationRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return card;

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

        Iterator<CardInformation> cardInformationIterator = cardInformationList.iterator();

        while (cardInformationIterator.hasNext()) {

            CardInformation cardInformation = cardInformationIterator.next();

            cardInformationMap.put(cardInformation.getId(), cardInformation);

        }

        return cardInformationMap;

    }

}
