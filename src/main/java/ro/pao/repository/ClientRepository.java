package ro.pao.repository;

import ro.pao.model.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepository extends Repository<Client> {

    Optional<Client> getObjectByBirthDate(LocalDate date) throws SQLException;
    
}
