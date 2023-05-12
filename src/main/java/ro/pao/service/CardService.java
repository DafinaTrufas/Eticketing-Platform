package ro.pao.service;

import ro.pao.model.CardInformation;
import ro.pao.model.Client;
import ro.pao.model.MailInformation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CardService extends Service<CardInformation> {

    Optional<CardInformation> getByName(String firstName, String lastName) throws SQLException;

}
