package ro.pao.repository;

import ro.pao.model.CardInformation;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface CardInformationRepository extends Repository<CardInformation> permits CardInformationRepositoryImpl {

    Optional<CardInformation> getObjectByName(String firstName, String lastName) throws SQLException;
    
}
