package com.johncnstn.report;

import com.johncnstn.data.entity.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class ParseDataToRunReport {

    @Autowired
    private RunReportList runReportList;

    public RunReportList setRunReport(List<Entry> allUsersEntries) {

        runReportList.getRunReportList().clear();

        Iterator<Entry> entryIterator = allUsersEntries.iterator();
        while (entryIterator.hasNext()) {
            getAllEntriesOfWeek(allUsersEntries);
        }

        return runReportList;
    }

    private List<Entry> getAllEntriesOfWeek(List<Entry> allUsersEntries) {

        List<Entry> allEntriesOfTheWeek = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(allUsersEntries.get(0).getStartRaceDateTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date dateOfMonday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date dateOfSunday = calendar.getTime();

        checkIfEntriesInTheSameWeek(allUsersEntries, allEntriesOfTheWeek, dateOfMonday, dateOfSunday);

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String weekData = formatter.format(dateOfMonday);
        weekData += " " + formatter.format(dateOfSunday);

        allUsersEntries.removeAll(allEntriesOfTheWeek);

        calculateDataForReport(allEntriesOfTheWeek, calendar, weekData);

        return allEntriesOfTheWeek;

    }

    private void calculateDataForReport(List<Entry> allEntriesOfTheWeek, Calendar calendar, String weekData) {

        int totalDistance = 0;
        long totalTime = 0;
        double avgSpeed;
        int amountOfEntries = 0;

        for (Entry entry : allEntriesOfTheWeek) {
            totalDistance += entry.getDistance();
            LocalTime localTime = entry.getRaceTime().toLocalTime();
            totalTime += parseLocalTimeToMS(localTime);
            amountOfEntries++;
        }

        avgSpeed = (1.0 * totalDistance) / (amountOfEntries * totalTime / 1000);

        String parsedSpeed = new DecimalFormat("#0.00").format(avgSpeed);

        long avgTime = totalTime / amountOfEntries;

        String formattedDuration = formatTime(avgTime);

        weekData = calendar.get(Calendar.WEEK_OF_YEAR) + " " + "(" + weekData + ")";

        runReportList.getRunReportList().add(setDataForReport(weekData, formattedDuration, parsedSpeed, totalDistance));
    }

    private void checkIfEntriesInTheSameWeek(List<Entry> allUsersEntries, List<Entry> allEntriesOfTheWeek, Date dateOfMonday, Date dateOfSunday) {
        for (Entry entry : allUsersEntries) {
            if ((entry.getStartRaceDateTime().after(dateOfMonday) &&
                    (entry.getStartRaceDateTime().before(dateOfSunday)))) {
                allEntriesOfTheWeek.add(entry);
            }
        }
    }

    private RunReport setDataForReport(String weekData, String formattedDuration, String parsedSpeed, int totalDistance) {
        RunReport runReport = new RunReport();
        runReport.setWeek(weekData);
        runReport.setAvgTime(formattedDuration);
        runReport.setAvgSpeed(parsedSpeed);
        runReport.setTotalDistance(totalDistance);
        return runReport;
    }

    private String formatTime(long avgTime) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(avgTime),
                TimeUnit.MILLISECONDS.toMinutes(avgTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(avgTime)),
                TimeUnit.MILLISECONDS.toSeconds(avgTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(avgTime)));
    }

    private long parseLocalTimeToMS(LocalTime localTime) {
        return ((localTime.getHour() * 60 * 60) + (localTime.getMinute() * 60) + localTime.getSecond()) * 1000;
    }
}
