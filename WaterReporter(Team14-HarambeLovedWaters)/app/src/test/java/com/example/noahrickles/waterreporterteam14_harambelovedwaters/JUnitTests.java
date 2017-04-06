package com.example.noahrickles.waterreporterteam14_harambelovedwaters;

import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.Singleton;
import com.example.noahrickles.waterreporterteam14_harambelovedwaters.model.WaterReport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * Created by Noah Rickles on 4/5/2017.
 */

public class JUnitTests {
    Singleton instance = Singleton.getInstance();

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
}
