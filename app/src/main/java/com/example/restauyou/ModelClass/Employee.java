package com.example.restauyou.ModelClass;

import java.util.Date;

public class Employee extends User{
    private String position, startingDate, currentStatus;
    private double hourlyRate;
    private double ytdHoursWorked, ytdTotalIncome;
    public Employee(){
        super();

    }
    public Employee(String id, String name, String email, String phone, String photoUrl,
                    String position, String startingDate, double hourlyRate) {

        super(name, email, photoUrl, id, "employee", phone);
        this.position = position;
        this.startingDate = startingDate;
        this.hourlyRate = hourlyRate;
        this.currentStatus = "active";
        this.ytdHoursWorked = 0;
        this.ytdTotalIncome = 0;
    }


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getYtdHoursWorked() {
        return ytdHoursWorked;
    }

    public void setYtdHoursWorked(double ytdHoursWorked) {
        this.ytdHoursWorked = ytdHoursWorked;
    }

    public double getYtdTotalIncome() {
        return ytdTotalIncome;
    }

    public void setYtdTotalIncome(double ytdTotalIncome) {
        this.ytdTotalIncome = ytdTotalIncome;
    }
}
