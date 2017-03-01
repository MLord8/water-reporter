package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Matt on 2/27/2017.
 */

public class WaterReport {

    private String dateAndTime;
    private String location;
    private String workerName;
    private int reportNumber;
    private String typeOfWater;
    private String conditionOfWater;

    public WaterReport(String dateAndTime, String location, String workerName,
                       int reportNumber, String typeOfWater, String conditionOfWater) {
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.workerName = workerName;
        this.reportNumber = reportNumber;
        this.typeOfWater = typeOfWater;
        this.conditionOfWater = conditionOfWater;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public String getLocation() {
        return location;
    }

    public String getWorkerName() {
        return workerName;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public String getTypeOfWater() {
        return typeOfWater;
    }

    public String getConditionOfWater() { return conditionOfWater; }

    public String toString() {
        return "Date/Time: " + dateAndTime + "\nLocation: " + location + "\nWorker name: " +
                workerName + "\nReport number: " + reportNumber + "\nType of Water: " +
                typeOfWater + "\nCondition of Water: " + conditionOfWater;
    }

}
