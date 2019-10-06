package com.aplusd.houserenter.rentlodging.contract.fragments.uielements;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aplusd.houserenter.R;
import com.aplusd.houserenter.rentlodging.contract.CalendarDecorator;
import com.aplusd.houserenter.rentlodging.contract.ViewModelContract;
import com.aplusd.houserenter.rentlodging.model.BookedDates;
import com.aplusd.houserenter.rentlodging.model.Contract;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Azamat Dzhonov
 * @date 13.04.2018
 */

public class DateFragment extends Fragment {

    private ViewModelContract viewModel = null;

    private MaterialCalendarView calendar = null;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(ViewModelContract.class);

        viewModel.getLodgingNotAvailableDate(getContext()).observe(this, new Observer<ArrayList<BookedDates>>() {
            @Override
            public void onChanged(@Nullable ArrayList<BookedDates> dates) {
                if(dates == null)
                    return;


                calendar.addDecorator(new CalendarDecorator(dates));
            }
        });
    }

        @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        calendar = (MaterialCalendarView) view.findViewById(R.id.calendar_view);

        Calendar dateFrom = Calendar.getInstance();
        Calendar dateTo = Calendar.getInstance();
        dateTo.add(Calendar.MONTH, 4);

        calendar.state().edit().setMinimumDate(dateFrom).setMaximumDate(dateTo).commit();


        calendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE);

        calendar.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                ArrayList<Date>selectedDates = new ArrayList<>();
                for(int i = 0; i <dates.size(); i++)
                    selectedDates.add(dates.get(i).getDate());

                if(dates.size() > 1)
                {
                    Contract contract = viewModel.getNewContract().getValue();
                    contract.setFromDate(selectedDates.get(0));
                    contract.setToDate(selectedDates.get(selectedDates.size() - 1));
                    contract.setDates(selectedDates);
                    viewModel.getNewContract().setValue(contract);

                }
                else
                    Toast.makeText(getContext(), R.string.please_select_dates, Toast.LENGTH_SHORT).show();
            }
        });




        return view;
    }
}
