package ro.pao.service;

import ro.pao.model.MailInformation;
import ro.pao.model.SportsLocation;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public sealed interface MailService extends Service<MailInformation> permits MailServiceImpl {

    Optional<MailInformation> getByAddress(String address) throws SQLException;

}
