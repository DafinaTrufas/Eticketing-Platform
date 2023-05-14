# E-ticketing System

## Part I

### Class Hierarchy
- Abstract classes:
- - Event => Concrete classes: CulturalEvent and SportsEvent
- - Location => Concrete classes: CulturalLocation and SportsLocation

- Concrete classes:
- - Client
- - CardInformation
- - MailInformation
- - Ticket

- Enum classes (used to define type of fields for other concrete classes):
- - CulturalEventType
- - SportsEventType
- - CulturalLocationType
- - SportsLocationType
- - TicketType

### Available functionalities
- CRUD operations for MailInformation
- CRUD operations for CulturalEvent
- CRUD operations for Location
- Sorting Locations in lexicographic order
- Sorting Clients by number of tickets they have purchased
- Sorting Events by StartDate
- Filter SportsEvents by their location-type
- Set DeleteTime for Events that have already started
- Get number of tickets for an event, grouped by ticket-category
- Functionality for "buying" and "selling" tickets for an event

## Part II

In the second part of this project, I added a connection to a Oracle Database, using JDBC, in order to ensure data persistency. I implemented
CRUD operations for all the entities in the system, added a Repository layer and switched to using this in the Services (instead of static maps).
Furthermore, I added a logging system, for logging error messages in a CSV file.
I also used the JacksonLibrary for fetching data from an API, and mapped the obtained JSONs to Java Objects.
