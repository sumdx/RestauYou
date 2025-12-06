package com.example.restauyou.ModelClass;

public class Schedule {

    private String day;
    private String shiftsScheduled;
    private String noShifts;

    public Schedule(String day, String shiftsScheduled, String noShifts) {
        this.day = day;
        this.shiftsScheduled = shiftsScheduled;
        this.noShifts = noShifts;
    }

    public String getDay() {
        return day;
    }

    public String getshiftsScheduled() {
        return shiftsScheduled;
    }

    public String getNoShifts() {
        return noShifts;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setShiftsScheduled(String shiftsScheduled) {
        this.shiftsScheduled = shiftsScheduled;
    }

    public void setNoShifts(String noShifts) {
        this.noShifts = noShifts;
    }
}

