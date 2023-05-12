package ro.pao.service.impl;

import lombok.RequiredArgsConstructor;
import ro.pao.model.CulturalLocation;
import ro.pao.model.SportsLocation;
import ro.pao.model.abstracts.Location;
import ro.pao.service.LocationService;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService<Location> {

    private final LocationService<CulturalLocation> culturalLocationService;

    private final LocationService<SportsLocation> sportsLocationService;

    @Override
    public Optional<Location> getById(UUID id) {

        try {

            return culturalLocationService.getById(id).map(Function.identity());

        } catch(RuntimeException ec) {

            try {

                return sportsLocationService.getById(id).map(Function.identity());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Optional<Location> getByCapacity(Integer capacity) {

        try {

            return culturalLocationService.getByCapacity(capacity).map(Function.identity());

        } catch(RuntimeException ec) {

            try {

                return sportsLocationService.getByCapacity(capacity).map(Function.identity());

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

    }

    @Override
    public Map<UUID, Location> getAllFromMap() {

        Map<UUID, Location> locationMap = new HashMap<>();

        locationMap.putAll(culturalLocationService.getAllFromMap());
        locationMap.putAll(sportsLocationService.getAllFromMap());

        return locationMap;

    }

    @Override
    public Map<UUID, Location> getAllFromMapWithCondition() {

        Map<UUID, Location> LocationMap = new HashMap<>();

        LocationMap.putAll(culturalLocationService.getAllFromMapWithCondition());
        LocationMap.putAll(sportsLocationService.getAllFromMapWithCondition());

        return LocationMap;

    }

    @Override
    public void addAllFromGivenMap(Map<UUID, Location> locationMap) throws SQLException {

        for (Location location : locationMap.values()) {

            addOnlyOne(location);

        }

    }

    @Override
    public void addOnlyOne(Location Location) throws SQLException {

        if (Location instanceof CulturalLocation culturalLocation) {

            culturalLocationService.addOnlyOne(culturalLocation);

        }

        else {

            sportsLocationService.addOnlyOne((SportsLocation) Location);

        }

    }

    @Override
    public void removeElementById(UUID id) {

        try {

            culturalLocationService.removeElementById(id);

        } catch(RuntimeException ec) {

            sportsLocationService.removeElementById(id);

        }

    }

    @Override
    public void updateElementById(UUID id, Location location) {

        if (location instanceof CulturalLocation culturalLocation) {

            culturalLocationService.updateElementById(id, culturalLocation);

        }

        else {

            sportsLocationService.updateElementById(id, (SportsLocation) location);

        }

    }

    @Override
    public Map<UUID, Location> listToMap(List<Location> locationList) {

        Map<UUID, Location> locationMap = new HashMap<>();

        for (Location location : locationList) {

            locationMap.put(location.getId(), location);

        }

        return locationMap;

    }

}
