package ro.pao.repository.impl;

import lombok.RequiredArgsConstructor;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.EventMapper;
import ro.pao.model.CulturalEvent;
import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsEvent;
import ro.pao.model.SportsLocation;
import ro.pao.model.abstracts.Event;
import ro.pao.model.abstracts.Location;
import ro.pao.repository.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final CulturalLocationRepository culturalLocationRepository;

    private final SportsLocationRepository sportsLocationRepository;

    @Override
    public Optional<Location> getObjectById(UUID id) throws SQLException {

        try {

            culturalLocationRepository.getObjectById(id);

        } catch (RuntimeException e) {

            sportsLocationRepository.getObjectById(id);

        }

        return Optional.empty();

    }

    @Override
    public void deleteObjectById(UUID id) {

        try {

            culturalLocationRepository.deleteObjectById(id);

        } catch (RuntimeException e) {

            sportsLocationRepository.deleteObjectById(id);

        }

    }

    @Override
    public void updateObjectById(UUID id, Location newObject) {

        if (newObject instanceof CulturalLocation culturalLocation) {

            culturalLocationRepository.updateObjectById(id, culturalLocation);

        }

        else {

            sportsLocationRepository.updateObjectById(id, (SportsLocation) newObject);

        }

    }

    @Override
    public void addNewObject (Location location) {

        if (location instanceof CulturalLocation culturalLocation) {

            culturalLocationRepository.addNewObject(culturalLocation);

        }

        else {

            sportsLocationRepository.addNewObject((SportsLocation) location);

        }

    }

    @Override
    public List<Location> getAll() {

        try {

            culturalLocationRepository.getAll();

        } catch (RuntimeException e) {

            sportsLocationRepository.getAll();

        }

        return List.of();

    }

    @Override
    public void addAllFromGivenList(List<Location> locationList) {

        for (Location location : locationList) {

            if (location instanceof CulturalLocation culturalLocation) {

                culturalLocationRepository.addNewObject(culturalLocation);

            }

            else {

                sportsLocationRepository.addNewObject((SportsLocation) location);

            }

        }

    }

}
