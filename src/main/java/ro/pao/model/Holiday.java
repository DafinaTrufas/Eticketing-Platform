package ro.pao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Holiday {

    private String name;

    private String name_local;

    private String language;

    private String description;

    private String country;

    private String location;

    private String type;

    private String date;

    private String date_year;

    private String date_month;

    private String date_day;

    private String week_day;

    @Override
    public String toString() {

        return "Holiday{" +
                "name='" + name + '\'' +
                ", name_local='" + name_local + '\'' +
                ", language='" + language + '\'' +
                ", description='" + description + '\'' +
                ", country='" + country + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                ", date='" + date + '\'' +
                ", date_year='" + date_year + '\'' +
                ", date_month='" + date_month + '\'' +
                ", date_day='" + date_day + '\'' +
                ", week_day='" + week_day + '\'' +
                '}';

    }

}
