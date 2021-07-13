package com.foxminded.university.model;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Timetable> timetables;

    public Timetable() {
       timetables = new ArrayList<>();
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }
}
