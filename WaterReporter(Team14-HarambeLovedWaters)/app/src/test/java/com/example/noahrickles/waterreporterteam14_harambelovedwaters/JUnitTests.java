package com.example.noahrickles.waterreporterteam14_harambelovedwaters;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterPurityReport;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by Noah Rickles on 4/5/2017.
 */

public class JUnitTests {
    private final Singleton instance = Singleton.getInstance();

    //Noah Rickles
    @Test
    public void testFindWaterReportById() throws Exception {
        assertNull(instance.findWaterReportById(0));
        assertNull(instance.findWaterReportById(1));
        assertNull(instance.findWaterReportById(2));
        assertNull(instance.findWaterReportById(3));
        assertNull(instance.findWaterReportById(Integer.MAX_VALUE));

        WaterReport w1 = new WaterReport("03/12/2017 06:47:50 PM (EDT)", "Topeka, Kansas",
                "noahrickles", 0, "Stream", "Potable");
        WaterReport w2 = new WaterReport("02/09/2017 04:09:57 PM (EDT)", "Atlanta, Georgia",
                "noahrickles", 1, "Lake", "Potable");
        WaterReport w3 = new WaterReport("12/20/2016 10:12:32 AM (EDT)", "Tampa, Florida",
                "noahrickles", 2, "Bottled", "Treatable-Clear");
        instance.getWaterReports().add(w1);
        instance.getWaterReports().add(w2);
        instance.getWaterReports().add(w3);
        assertEquals(w1, instance.findWaterReportById(0));
        assertEquals(w2, instance.findWaterReportById(1));
        assertEquals(w3, instance.findWaterReportById(2));

        assertNull(instance.findWaterReportById(3));
        assertNull(instance.findWaterReportById(Integer.MAX_VALUE));
        assertNull(instance.findWaterReportById(-1));
        assertNull(instance.findWaterReportById(Integer.MIN_VALUE));

        instance.getWaterReports().clear();
        WaterReport w4 = new WaterReport("05/15/1997 05:15:09 PM (EDT)", "Duluth, Georgia",
                "noahrickles", 100, "Stream", "Treatable-Clear");
        instance.getWaterReports().add(w1);
        instance.getWaterReports().add(w4);

        assertEquals(w1, instance.findWaterReportById(0));
        assertEquals(w4, instance.findWaterReportById(100));
        assertNull(instance.findWaterReportById(1));
        assertNull(instance.findWaterReportById(2));
        assertNull(instance.findWaterReportById(99));
    }

    // Nikhil Ramesh
    @Test
    public void testGetCPPMGraphPoints() throws Exception {
        assertNull(instance.getCPPMGraphPoints(null, "2017"));
        assertNull(instance.getCPPMGraphPoints("Tokyo, Japan", null));
        assertNull(instance.getCPPMGraphPoints(null, null));

        HashMap<Integer, Double> graphPoints = new HashMap<>();
        assertEquals(instance.getCPPMGraphPoints("Tokyo, Japan", "2015"), graphPoints);

        WaterPurityReport wp1 = new WaterPurityReport("06/10/2010 02:15:15 PM (EDT)",
                "Tokyo, Japan", "nramesh", 1, 2.0, 2.0, "Treatable");
        instance.getWaterPurityReports().add(wp1);
        graphPoints.put(6, 2.0);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Tokyo, Japan", "2010"));

        WaterPurityReport wp2 = new WaterPurityReport("06/21/2010 03:15:15 PM (EDT)",
                "Tokyo, Japan", "nramesh", 1, 6.0, 2.0, "Safe");
        instance.getWaterPurityReports().add(wp2);
        graphPoints.put(6, 4.0);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Tokyo, Japan", "2010"));

        WaterPurityReport wp3 = new WaterPurityReport("08/27/2010 03:15:15 PM (EDT)",
                "Tokyo, Japan", "nramesh", 1, 10.0, 2.0, "Treatable");
        instance.getWaterPurityReports().add(wp3);
        graphPoints.put(8, 10.0);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Tokyo, Japan", "2010"));

        WaterPurityReport wp4 = new WaterPurityReport("08/27/2012 03:15:15 PM (EDT)",
                "Tokyo, Japan", "nramesh", 1, 10.0, 2.0, "Treatable");
        instance.getWaterPurityReports().add(wp4);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Tokyo, Japan", "2010"));

        WaterPurityReport wp5 = new WaterPurityReport("08/27/2012 03:15:15 PM (EDT)",
                "Sydney, Australia", "nramesh", 1, 10.0, 2.0, "Treatable");
        instance.getWaterPurityReports().add(wp5);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Tokyo, Japan", "2010"));
    }

    //Matt Lord
    @Test
    public void testGetVPPMGraphPoints() throws Exception {
        assertNull(instance.getVPPMGraphPoints(null, "2016"));
        assertNull(instance.getVPPMGraphPoints("Charleston, West Virginia", null));
        assertNull(instance.getVPPMGraphPoints(null, null));

        HashMap<Integer, Double> graphPoints = new HashMap<>();
        WaterPurityReport wp1 = new WaterPurityReport("04/01/2017 02:15:15 PM (EDT)",
                "Atlanta, Georgia", "BoolinBob420", 1, 3.0, 3.0, "Treatable");
        instance.getWaterPurityReports().add(wp1);
        graphPoints.put(4, 3.0);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Atlanta, Georgia", "2017"));

        WaterPurityReport wp2 = new WaterPurityReport("04/02/2017 05:15:15 AM (EDT)",
                "Atlanta, Georgia", "BoolinBob420", 1, 9.0, 9.0, "Treatable");
        instance.getWaterPurityReports().add(wp2);
        graphPoints.put(4, 6.0);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Atlanta, Georgia", "2017"));

        WaterPurityReport wp3 = new WaterPurityReport("11/02/2013 05:15:15 AM (EDT)",
                "New York, New York", "BoolinBob420", 1, 10.0, 12.0, "Treatable");
        instance.getWaterPurityReports().add(wp3);
        graphPoints.put(4, 6.0);
        assertEquals(graphPoints, instance.getCPPMGraphPoints("Atlanta, Georgia", "2017"));
    }
}
