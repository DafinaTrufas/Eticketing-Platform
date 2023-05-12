package ro.pao.repository;

import ro.pao.model.MailInformation;
import ro.pao.repository.MailInformationRepositoryImpl;

import java.sql.SQLException;
import java.util.Optional;

public sealed interface MailInformationRepository extends Repository<MailInformation> permits MailInformationRepositoryImpl {

    Optional<MailInformation> getObjectByAddress(String address) throws SQLException;
    
}
