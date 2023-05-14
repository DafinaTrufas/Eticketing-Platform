package ro.pao.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

public class Holiday {

    private String name;

    private String name_local;

    private String language;

    private String description;

    private String country;

    private String location;

    private String type;

    private LocalDate date;

    private Year date_year;

    private Month date_month;

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
                ", date=" + date +
                ", date_year=" + date_year +
                ", date_month=" + date_month +
                ", date_day='" + date_day + '\'' +
                ", week_day='" + week_day + '\'' +
                '}';
    }

}
