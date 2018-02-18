package com.johncnstn.model;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.entity.User;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.report.CurrencyRate;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DataToCurrencyRate {

    @Qualifier("entryRepository")
    @Autowired
    private EntryRepository entryRepository;


    @Autowired
    private CurrencyRate currencyRate;

    public CurrencyRate da(User user) {

        Calendar calendar = Calendar.getInstance();

        List<Entry> allUsersEntries = entryRepository.findAllByUserId(user.getId());

//        for (Entry entry : allUsersEntries) {
//            List<Entry> allEntriesOfTheWeek = getAllEntriesOfWeek(allUsersEntries, calendar);
//        }

        getAllEntriesOfWeek(allUsersEntries, calendar);

//        calendar.setTime(date); // Устанавливаем текущее время
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //Устанавливаем понедельник на календаре, будто сейчас понедельник
//
//        SimpleDateFormat sdf= new SimpleDateFormat("dd-mm-yyyy");
//
//
//
////        cr.setWeek(Calendar.WEEK_OF_YEAR);
//        cr.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
//
//        for(int i = 0; i < 7; i++){
//            cr.setDate(date);
//            calendar.add(Calendar.DAY_OF_WEEK, 1); //Прибавляем сутки
//        }


        return currencyRate;
    }

    private List<Entry> getAllEntriesOfWeek(List<Entry> allUsersEntries, Calendar calendar) {

        List<Entry> allEntriesOfTheWeek = new ArrayList<>();

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

        for (Entry entry : allEntriesOfTheWeek) {
            allUsersEntries.remove(entry);
        }

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
        currencyRate.setWeek(weekData);
        currencyRate.setAvgTime(formattedDuration);
        currencyRate.setAvgSpeed(parsedSpeed);
        currencyRate.setTotalDistance(totalDistance);

    }

    private long parseLocalTimeToMS(LocalTime localTime) {
        return ((localTime.getHour() * 60 * 60) + (localTime.getMinute() * 60) + localTime.getSecond()) * 1000;
    }
}
