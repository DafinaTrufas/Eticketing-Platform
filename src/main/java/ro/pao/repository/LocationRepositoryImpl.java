package ro.pao.repository;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsLocation;
import ro.pao.model.abstracts.Location;
import ro.pao.repository.LocationRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public non-sealed class LocationRepositoryImpl implements LocationRepository<Location> {

    private final LocationRepository<CulturalLocation> culturalLocationRepository;

    private final LocationRepository<SportsLocation> sportsLocationRepository;

    @Override
    public Optional<Location> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {

        try {

            culturalLocationRepository.getObjectById(id);

        } catch (RuntimeException | ObjectNotFoundException e) {

            sportsLocationRepository.getObjectById(id);

        }

        return Optional.empty();

    }

    @Override
    public Optional<Location> getObjectByCapacity(Integer capacity) throws SQLException {

        try {

            culturalLocationRepository.getObjectByCapacity(capacity);

        } catch (RuntimeException e) {

            sportsLocationRepository.getObjectByCapacity(capacity);

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
    public void addNewObject (Location location) throws SQLException {

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
    public void addAllFromGivenList(List<Location> locationList) throws SQLException {

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
