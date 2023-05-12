package ro.pao;

import lombok.RequiredArgsConstructor;
import ro.pao.application.Menu;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.repository.CulturalEventRepository;
import ro.pao.repository.impl.CulturalEventRepositoryImpl;
import ro.pao.repository.impl.EventRepositoryImpl;
import ro.pao.service.impl.CulturalEventServiceImpl;

import java.sql.*;
import java.util.Scanner;
import java.util.UUID;

public class Main {

    public static void main(String[] args) throws SQLException {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            Menu menu = Menu.getInstance();

            menu.addEventOfEachType();

            menu.updateEvent();

            menu.addLocationOfEachType();

            menu.deleteLocation();

            menu.addAndFilterClients();

            menu.addAndFilterTickets();

            if ("exit".equals(scanner.next())) {

                break;

            }

        }

    }

}
