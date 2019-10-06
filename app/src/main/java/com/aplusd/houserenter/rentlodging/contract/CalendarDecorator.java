package com.aplusd.houserenter.rentlodging.contract;

import com.aplusd.houserenter.rentlodging.model.BookedDates;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Azamat Dzhonov
 * @date 28.05.2018
 */
public class CalendarDecorator implements DayViewDecorator {


    ArrayList<CalendarDay> calendarDays = null;

    public CalendarDecorator(ArrayList<BookedDates> dates)
    {
        calendarDays = new ArrayList<>();
       for(int i = 0; i < dates.size(); i++)
       {
            Date from = dates.get(i).getFrom();
            Date to = dates.get(i).getTo();
            calendarDays.add(CalendarDay.from(to));
            calendarDays.add(CalendarDay.from(from));

            while (from.before(to))
            {
              Calendar calendar = Calendar.getInstance();
              calendar.setTime(from);
              calendar.add(Calendar.DAY_OF_MONTH, 1);
              from = calendar.getTime();
              calendarDays.add(CalendarDay.from(from));
            }

       }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return calendarDays.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setDaysDisabled(true);
    }
}
