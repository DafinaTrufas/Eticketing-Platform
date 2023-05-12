package ro.pao.repository;

import ro.pao.model.CulturalLocation;
import ro.pao.model.MailInformation;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MailInformationRepository extends Repository<MailInformation> {

    Optional<MailInformation> getObjectByAddress(String address) throws SQLException;
    
}
