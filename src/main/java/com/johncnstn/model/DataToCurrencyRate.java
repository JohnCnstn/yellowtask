package com.johncnstn.model;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.report.CurrencyRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class DataToCurrencyRate {

    @Autowired
    private List<CurrencyRate> currencyRateList;

    public List<CurrencyRate> da(List<Entry> allUsersEntries) {

        Iterator<Entry> crunchifyIterator = allUsersEntries.iterator();
        while (crunchifyIterator.hasNext()) {
            getAllEntriesOfWeek(allUsersEntries);
        }

        return currencyRateList;
    }

    private List<Entry> getAllEntriesOfWeek(List<Entry> allUsersEntries) {

        Calendar calendar = Calendar.getInstance();

        List<Entry> allEntriesOfTheWeek = new ArrayList<>();

        calendar.setTime(allUsersEntries.get(0).getStartRaceDateTime());

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        Date dateOfMonday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 7);
        Date dateOfSunday = calendar.getTime();

        for (Entry entry : allUsersEntries) {
            if ((entry.getStartRaceDateTime().after(dateOfMonday) &&
                    (entry.getStartRaceDateTime().before(dateOfSunday)))) {
                allEntriesOfTheWeek.add(entry);
            }
        }

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        String weekData = formatter.format(dateOfMonday);
        weekData += " " + formatter.format(dateOfSunday);

        allUsersEntries.removeAll(allEntriesOfTheWeek);

        setDataForReport(allEntriesOfTheWeek, calendar, weekData);

        return allEntriesOfTheWeek;

    }

    private void setDataForReport(List<Entry> allEntriesOfTheWeek, Calendar calendar, String weekData) {

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

        System.out.println(parsedSpeed);

        long avgTime = totalTime / amountOfEntries;

        String formattedDuration = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(avgTime),
                TimeUnit.MILLISECONDS.toMinutes(avgTime) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(avgTime)),
                TimeUnit.MILLISECONDS.toSeconds(avgTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(avgTime)));


        weekData += " " + calendar.get(Calendar.WEEK_OF_YEAR);

        CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setWeek(weekData);
        currencyRate.setAvgTime(formattedDuration);
        currencyRate.setAvgSpeed(parsedSpeed);
        currencyRate.setTotalDistance(totalDistance);
        currencyRateList.add(currencyRate);
    }

    private long parseLocalTimeToMS(LocalTime localTime) {
        return ((localTime.getHour() * 60 * 60) + (localTime.getMinute() * 60) + localTime.getSecond()) * 1000;
    }
}
