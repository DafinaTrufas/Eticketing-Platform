package ro.pao.mapper;

import ro.pao.model.CardInformation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CardInformationMapper {

    private static final CardInformationMapper INSTANCE = new CardInformationMapper();

    CardInformationMapper() {
    }

    public static CardInformationMapper getInstance() {
        return INSTANCE;
    }


    public Optional<CardInformation> mapToCardInformation(ResultSet resultSet) throws SQLException {

        if (resultSet.next()) {

            return Optional.of(
                    CardInformation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .balance(resultSet.getString("firstname"))
                            .firstNameOwner(resultSet.getString("firstname"))
                            .lastNameOwner(resultSet.getString("lastname"))
                            .cardNumber(resultSet.getString("cardNumber"))
                            .build()
            );

        } else {

            return Optional.empty();

        }

    }

    public List<CardInformation> mapToCardInformationList(ResultSet resultSet) throws SQLException {

        List<CardInformation> CardInformationList = new ArrayList<>();

        while (resultSet.next()) {

            CardInformationList.add(
                    CardInformation.builder()
                            .id(UUID.fromString(resultSet.getString("id")))
                            .balance(resultSet.getString("firstname"))
                            .firstNameOwner(resultSet.getString("firstname"))
                            .lastNameOwner(resultSet.getString("lastname"))
                            .cardNumber(resultSet.getString("cardNumber"))
                            .build()
            );

        }

        return CardInformationList;

    }

}
