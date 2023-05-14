package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.application.csv.CSVFormatter;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.SportsEvent;
import ro.pao.repository.EventRepository;

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
public non-sealed class SportsEventServiceImpl implements EventService<SportsEvent> {

    private final EventRepository<SportsEvent> sportsEventRepository;

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
    public Optional<SportsEvent> getById(UUID id) throws SQLException {

        Optional<SportsEvent> sportsEvent = Optional.empty();

        try {

            sportsEvent = sportsEventRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return sportsEvent;

    }

    @Override
    public Optional<SportsEvent> getByLocation(UUID id) throws SQLException {

        Optional<SportsEvent> sportsEvent = sportsEventRepository.getObjectByLocation(id);

        if(sportsEvent.isEmpty()) {
            throw new RuntimeException("There is no SportsEvent in this location.");
        }

        return sportsEvent;

    }

    @Override
    public Map<UUID, SportsEvent> getAllFromMap() {

        return listToMap(sportsEventRepository.getAll());

    }

    @Override
    public Map<UUID, SportsEvent> getAllFromMapWithCondition() {

        return listToMap(sportsEventRepository.getAll().stream()
                .filter(sportsEvent -> sportsEvent.getStage().contains("final"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, SportsEvent> sportsEventMap) throws SQLException {

        sportsEventRepository.addAllFromGivenList(sportsEventMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(SportsEvent sportsEvent) throws SQLException {

        sportsEventRepository.addNewObject(sportsEvent);

    }

    @Override
    public void removeElementById(UUID id) {

        sportsEventRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, SportsEvent newSportsEvent) {

        sportsEventRepository.updateObjectById(id, newSportsEvent);

    }

    @Override
    public Map<UUID, SportsEvent> listToMap(List<SportsEvent> sportsEventList) {

        Map<UUID, SportsEvent> sportsEventMap = new HashMap<>();

        Iterator<SportsEvent> sportsEventIterator = sportsEventList.iterator();

        while (sportsEventIterator.hasNext()) {

            SportsEvent sportsEvent = sportsEventIterator.next();

            sportsEventMap.put(sportsEvent.getId(), sportsEvent);

        }

        return sportsEventMap;

    }

}
