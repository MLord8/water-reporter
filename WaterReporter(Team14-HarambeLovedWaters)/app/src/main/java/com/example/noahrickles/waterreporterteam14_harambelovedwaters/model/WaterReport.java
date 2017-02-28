package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Matt on 2/27/2017.
 */

public class WaterReport {

    private String dateAndTime;
    private String location;
    private String workerName;
    private int reportNumber;
    private String condition;
    private double virusPPM;
    private double contaminantPPM;

    public WaterReport(String dateAndTime, String location, String workerName,
                       int reportNumber, String condition, double virusPPM, double contaminantPPM) {
        this.dateAndTime = dateAndTime;
        this.location = location;
        this.workerName = workerName;
        this.reportNumber = reportNumber;
        this.condition = condition;
        this.virusPPM = virusPPM;
        this.contaminantPPM = contaminantPPM;
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

    public String getCondition() {
        return condition;
    }

    public double getVirusPPM() {
        return virusPPM;
    }

    public double getContaminantPPM() {
        return getContaminantPPM();
    }

    public String toString() {
        return "Date/Time: " + dateAndTime + "\nLocation: " + location + "\nWorker name: " +
                workerName + "\nReport number: " + reportNumber + "\nCondition: " +
                condition + "\nVirusPPM: " + virusPPM + "\nContaminant PPM: " + contaminantPPM;
    }

}
