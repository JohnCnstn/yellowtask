package com.johncnstn.model;

import com.johncnstn.data.entity.Entry;
import com.johncnstn.data.repository.EntryRepository;
import com.johncnstn.report.CurrencyRate;
import com.johncnstn.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

        for (Entry entry : allEntriesOfTheWeek) {
            allUsersEntries.remove(entry);
        }

        setDataForReport(allEntriesOfTheWeek, calendar);

        return allEntriesOfTheWeek;

    }

    private void setDataForReport(List<Entry> allEntriesOfTheWeek, Calendar calendar) {

        int totalDistance = 0;

        for (Entry entry : allEntriesOfTheWeek) {
            totalDistance += entry.getDistance();
        }

        currencyRate.setWeek(calendar.get(Calendar.WEEK_OF_YEAR));
        currencyRate.setTotalDistance(totalDistance);

    }
}
