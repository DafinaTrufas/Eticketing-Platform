package ro.pao;

import ro.pao.application.Menu;

import java.sql.*;
import java.util.Scanner;

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

            menu.addTickets();

            menu.getByTypeTicket();

            menu.getClientById();

            if ("exit".equals(scanner.next())) {

                break;

            }

        }

    }

}
