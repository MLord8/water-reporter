package com.example.noahrickles.waterreporterteam14_harambelovedwaters.model;

/**
 * Created by Matt on 2/27/2017.
 */

@SuppressWarnings("DefaultFileTemplate")
public class WaterReport {

    private String dateAndTime;
    private String addressStr;
    private String username;
    private int reportNumber = 0;
    private String typeOfWater;
    private String conditionOfWater;

    public WaterReport() {

    }

    /**
     * Constructor for the water report object
     * @param dateAndTime       A String representation of the current date and time of the report
     * @param addressStr        The address the water report is located at
     * @param username          The user submitting the report
     * @param reportNumber      Autogenerated report number
     * @param typeOfWater       The type of the water source
     * @param conditionOfWater  The drinking condition of the water
     */
    public WaterReport(String dateAndTime, String addressStr, String username,
                       int reportNumber, String typeOfWater, String conditionOfWater) {
        this.dateAndTime = dateAndTime;
        this.addressStr = addressStr;
        this.username = username;
        this.reportNumber = reportNumber;
        this.typeOfWater = typeOfWater;
        this.conditionOfWater = conditionOfWater;
    }

    /**
     * Gets the date and time of a WaterReport
     * @return the WaterReport's dateAndTime
     */
    public String getDateAndTime() {
        return dateAndTime;
    }

    /**
     * Gets the address of a WaterReport
     * @return the WaterReport's address
     */
    public String getAddressStr() {
        return addressStr;
    }

    //public String findAddressString() throws IOException {
        //Address address = findAddress();
        //StringBuilder output = new StringBuilder();
        //int maxLine = address.getMaxAddressLineIndex();
        //for (int i = 0; i <= maxLine; i++) {
        //    output.append("\n" + address.getAddressLine(i));
        //}
        //return output.toString();
    //}

//    /**
//     *
//     * Gets the location of a WaterReport
//     * @return the WaterReport's location
//     */
    //public LatLng findLocation() throws IOException {
    //    Address address = findAddress();
    //    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
     //   return latLng;
    //}

    /**
     * Gets the username of a WaterReport
     * @return the WaterReport's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the report number of a WaterReport
     * @return the WaterReport's reportNumber
     */
    public int getReportNumber() {
        return reportNumber;
    }

    /**
     * Gets the type of water of a WaterReport
     * @return the WaterReport's typeOfWater
     */
    public String getTypeOfWater() {
        return typeOfWater;
    }

    /**
     * Gets the condition of water of a WaterReport
     * @return the WaterReport's conditionOfWater
     */
    public String getConditionOfWater() {
        return conditionOfWater;
    }

    /**
     * Gets a String representation of the WaterReport, including important
     * information about the WaterReport.
     * @return the String representation of the User
     */
    public String toString() {
        return "Date/Time: " + dateAndTime
                + "\nAddress: " + getAddressStr()
                //+ "\nLat/Lng: (" + String.format("%.3f", address.getLatitude())
                        //+ ", " + String.format("%.3f", address.getLongitude())
                + "\nSubmitter: " + username
                + "\nReport number: " + reportNumber
                + "\nType of Water: " + typeOfWater
                + "\nCondition of Water: " + conditionOfWater;
    }
}
