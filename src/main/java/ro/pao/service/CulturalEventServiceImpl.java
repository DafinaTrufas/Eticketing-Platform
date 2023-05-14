package ro.pao.service;

import lombok.RequiredArgsConstructor;
import ro.pao.application.csv.CSVFormatter;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.CulturalEvent;
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
public non-sealed class CulturalEventServiceImpl implements EventService<CulturalEvent> {

    private final EventRepository<CulturalEvent> culturalEventRepository;

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
    public Optional<CulturalEvent> getById(UUID id) throws SQLException {

        Optional<CulturalEvent> culturalEvent = Optional.empty();

        try {

            culturalEvent = culturalEventRepository.getObjectById(id);

        } catch (ObjectNotFoundException e) {

            logger.log(Level.WARNING, e.getMessage());

        } catch (Exception e) {

            logger.log(Level.SEVERE, e.getMessage());

        }

        return culturalEvent;

    }

    @Override
    public Optional<CulturalEvent> getByLocation(UUID id) throws SQLException {

        Optional<CulturalEvent> culturalEvent = culturalEventRepository.getObjectByLocation(id);

        if(culturalEvent.isEmpty()) {
            throw new RuntimeException("There is no CulturalEvent in this location.");
        }

        return culturalEvent;

    }

    @Override
    public Map<UUID, CulturalEvent> getAllFromMap() {

        return listToMap(culturalEventRepository.getAll());

    }

    @Override
    public Map<UUID, CulturalEvent> getAllFromMapWithCondition() {

        return listToMap(culturalEventRepository.getAll().stream()
                .filter(culturalEvent -> culturalEvent.getTitle().contains("spectacol"))
                .collect(Collectors.toList()));

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CulturalEvent> culturalEventMap) throws SQLException {

        culturalEventRepository.addAllFromGivenList(culturalEventMap.values().stream().toList());

    }

    @Override
    public void addOnlyOne(CulturalEvent culturalEvent) throws SQLException {

        culturalEventRepository.addNewObject(culturalEvent);

    }

    @Override
    public void removeElementById(UUID id) {

        culturalEventRepository.deleteObjectById(id);

    }

    @Override
    public void updateElementById(UUID id, CulturalEvent newCulturalEvent) {

        culturalEventRepository.updateObjectById(id, newCulturalEvent);

    }

    @Override
    public Map<UUID, CulturalEvent> listToMap(List<CulturalEvent> culturalEventList) {

        Map<UUID, CulturalEvent> culturalEventMap = new HashMap<>();

        Iterator<CulturalEvent> culturalEventIterator = culturalEventList.iterator();

        while (culturalEventIterator.hasNext()) {

            CulturalEvent culturalEvent = culturalEventIterator.next();

            culturalEventMap.put(culturalEvent.getId(), culturalEvent);

        }

        return culturalEventMap;

    }

}
