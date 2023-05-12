package ro.pao.mapper;

import ro.pao.model.MailInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MailInformationMapper {

    private static final MailInformationMapper INSTANCE = new MailInformationMapper();

    MailInformationMapper() {
    }

    public static MailInformationMapper getInstance() {
        return INSTANCE;
    }


    public Optional<MailInformation> mapToMailInformation(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    MailInformation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .firstName(resultSet.getString("firstname"))
                            .lastName(resultSet.getString("lastname"))
                            .address(resultSet.getString("address"))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<MailInformation> mapToMailInformationList(ResultSet resultSet) throws SQLException {

        List<MailInformation> MailInformationList = new ArrayList<>();

        while (resultSet.next()) {

            MailInformationList.add(
                    MailInformation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .firstName(resultSet.getString("firstname"))
                            .lastName(resultSet.getString("lastname"))
                            .address(resultSet.getString("address"))
                            .build()
            );

        }

        return MailInformationList;

    }

}
