package ro.pao.application;

import ro.pao.model.*;
import ro.pao.model.abstracts.Event;
import ro.pao.model.abstracts.Location;
import ro.pao.model.enums.*;
import ro.pao.repository.*;
import ro.pao.service.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Menu {

    private static Menu INSTANCE;

    private final EventService<Event> eventService = new EventServiceImpl(new CulturalEventServiceImpl(new CulturalEventRepositoryImpl()), new SportsEventServiceImpl(new SportsEventRepositoryImpl()));

    private final EventService<CulturalEvent> culturalEventService = new CulturalEventServiceImpl(new CulturalEventRepositoryImpl());

    private final EventService<SportsEvent> sportsEventService = new SportsEventServiceImpl(new SportsEventRepositoryImpl());

    private final LocationService<Location> locationService = new LocationServiceImpl(new CulturalLocationServiceImpl(new CulturalLocationRepositoryImpl()), new SportsLocationServiceImpl(new SportsLocationRepositoryImpl()));

    private final ClientService clientService = new ClientServiceImpl(new ClientRepositoryImpl());

    private final TicketService ticketService = new TicketServiceImpl(new TicketRepositoryImpl());

    public static Menu getInstance() {
        return (INSTANCE == null ? new Menu() : INSTANCE);
    }

    private static final Logger logger = Logger.getGlobal();

    public void addEventOfEachType() throws SQLException {

        CulturalEvent culturalEvent = CulturalEvent.builder()
                                        .id(UUID.randomUUID())
                                        .title("Hamlet")
                                        .description("avanpremiera")
                                        .build();

        SportsEvent sportsEvent = SportsEvent.builder()
                                    .id(UUID.randomUUID())
                                    .competition("Campionatul National de Inot")
                                    .stage("finala")
                                    .build();


        eventService.addOnlyOne(culturalEvent);
        eventService.addOnlyOne(sportsEvent);

        System.out.println("Evenimentele culturale:");
        culturalEventService.getAllFromMap().entrySet().stream().forEach(System.out::println);
        System.out.println("\nEvenimentele sportive:");
        sportsEventService.getAllFromMap().entrySet().stream().forEach(System.out::println);
        System.out.println("\nToate evenimentele:");
        eventService.getAllFromMap().entrySet().stream().forEach(System.out::println);

    }

    public void updateEvent() {

        Event event = CulturalEvent.builder()
                        .id(UUID.randomUUID())
                        .title("Furtuna")
                        .description("avanpremiera")
                        .build();

        try {

            eventService.updateElementById(UUID.fromString(eventService.getAllFromMap().keySet().stream().findFirst().get().toString()), event);

        } catch(RuntimeException e) {

            System.out.println("Nu exista evenimente in baza de date");

        }

        System.out.println("\nToate evenimentele, dupa updatare:");
        eventService.getAllFromMap().entrySet().stream().forEach(System.out::println);

    }

    public void addLocationOfEachType() throws SQLException {

        Map<UUID, Location> locationMap = new HashMap<>();

        CulturalLocation culturalLocation = CulturalLocation.builder()
                                                .id(UUID.randomUUID())
                                                .name("Opera Nationala Bucuresti")
                                                .address("Bulevardul Mihail Kogalniceanu 70-72, Bucuresti")
                                                .culturalEventLocationType(CulturalLocationType.OPERA_HOUSE)
                                                .capacity(1200)
                                                .build();

        SportsLocation sportsLocation = SportsLocation.builder()
                                            .id(UUID.randomUUID())
                                            .name("Bazinul Olimpic Bucuresti")
                                            .address("Belvedere, Bucuresti")
                                            .sportsLocationType(SportsLocationType.POOL)
                                            .capacity(800)
                                            .build();

        locationMap.put(culturalLocation.getId(), culturalLocation);
        locationMap.put(sportsLocation.getId(), sportsLocation);

        locationService.addAllFromGivenMap(locationMap);

        System.out.println("\nToate locatiile:");
        locationService.getAllFromMap().entrySet().stream().forEach(System.out::println);

    }

    public void deleteLocation() {

        try {

            locationService.removeElementById(UUID.fromString(locationService.getAllFromMap().keySet().stream().findFirst().get().toString()));

        } catch(RuntimeException e) {

            System.out.println("\nNu exista locatii in baza de date.");

        }

        System.out.println("\nToate locatiile, dupa stergere:");
        locationService.getAllFromMap().entrySet().stream().forEach(System.out::println);

    }

    public void addAndFilterClients() throws SQLException {

        Map<UUID, Client> clientMap = Stream.of(Client.builder()
                                                    .id(UUID.randomUUID())
                                                    .firstName("John")
                                                    .lastName("Miller")
                                                    .address("Bulevardul Regina Elisabeta 19, Bucuresti")
                                                    .birthDate(LocalDate.of(1972, 12, 5))
                                                    .build(),
                                                Client.builder()
                                                        .id(UUID.randomUUID())
                                                        .firstName("Tina")
                                                        .lastName("Popescu")
                                                        .address("Bulevardul Lascar Catargiu 21, Bucuresti")
                                                        .birthDate(LocalDate.of(1984, 7, 6))
                                                        .build()
                                            ).collect(Collectors.toMap(Client::getId, Function.identity()));;

        clientService.addAllFromGivenMap(clientMap);

        System.out.println("\nLista clientilor, dupa filtrare (numele contine 'escu'):");
        clientService.getAllFromMapWithCondition().entrySet().stream().forEach(System.out::println);

    }

    public void addTickets() throws SQLException {

        Map<UUID, Ticket> ticketMap = Stream.of(
                                        Ticket.builder()
                                                .id(UUID.randomUUID())
                                                .price(55.d)
                                                .ticketType(TicketType.MEDIUM)
                                                .eventId(UUID.fromString(eventService.getAllFromMap().keySet().stream().findFirst().get().toString()))
                                                .clientId(UUID.fromString(clientService.getAllFromMap().keySet().stream().findFirst().get().toString()))
                                                .build(),
                                        Ticket.builder()
                                                .id(UUID.randomUUID())
                                                .price(200.d)
                                                .ticketType(TicketType.VIP)
                                                .eventId(UUID.fromString(eventService.getAllFromMap().keySet().stream().findAny().get().toString()))
                                                .clientId(UUID.fromString(clientService.getAllFromMap().keySet().stream().findAny().get().toString()))
                                                .build()
        ).collect(Collectors.toMap(Ticket::getId, Function.identity()));

        ticketService.addAllFromGivenMap(ticketMap);

        System.out.println("\nLista biletelor:");
        ticketService.getAllFromMap().entrySet().stream().forEach(System.out::println);

    }

    public void getByTypeTicket() throws SQLException {

        Optional<Ticket> ticket = ticketService.getByType(TicketType.VIP);

        if (ticket.isPresent()) {

            System.out.println("\nDetaliile unui bilet de categorie VIP:\n" + ticket + '\n');

        }

    }

    public void getClientById() throws SQLException {

        Optional<Client> client = clientService.getById(UUID.fromString("99e4a3d1-9c0f-4a01-9f51-5e7dce433c34"));

        if (client.isPresent()) {

            System.out.println(client);

        }

    }

}
