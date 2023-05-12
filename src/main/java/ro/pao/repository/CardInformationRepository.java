package ro.pao.repository;

import ro.pao.model.CardInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardInformationRepository extends Repository<CardInformation> {

    Optional<CardInformation> getObjectByName(String firstName, String lastName) throws SQLException;
    
}
