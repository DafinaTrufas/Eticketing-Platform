package ro.pao.repository;

import ro.pao.model.Client;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public sealed interface ClientRepository extends Repository<Client> permits ClientRepositoryImpl {

    Optional<Client> getObjectByBirthDate(LocalDate date) throws SQLException;

}
