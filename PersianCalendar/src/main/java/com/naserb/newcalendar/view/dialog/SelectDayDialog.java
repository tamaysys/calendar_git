package com.naserb.newcalendar.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.naserb.newcalendar.view.daypickerview.DayPickerView;
import com.naserb.newcalendar.view.daypickerview.SimpleDayPickerView;
import com.naserb.newcalendar.view.fragment.CalendarFragment;

import com.naserb.newcalendar.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Created by ebrahim on 3/20/16.
 */
public class SelectDayDialog extends AppCompatDialogFragment {
    private static String BUNDLE_KEY = "jdn";

    public static SelectDayDialog newInstance(long jdn) {
        Bundle args = new Bundle();
        args.putLong(BUNDLE_KEY, jdn);

        SelectDayDialog fragment = new SelectDayDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        long jdn = args.getLong(BUNDLE_KEY, -1);

        DayPickerView dayPickerView = new SimpleDayPickerView(getContext());
        dayPickerView.setDayJdnOnView(jdn);

        return new AlertDialog.Builder(getActivity())
                .setView((View) dayPickerView)
                .setCustomTitle(null)
                .setPositiveButton(R.string.go, (dialogInterface, i) -> {
                    CalendarFragment calendarFragment = (CalendarFragment) getActivity()
                            .getSupportFragmentManager()
                            .findFragmentByTag(CalendarFragment.class.getName());

                    long resultJdn = dayPickerView.getDayJdnFromView();
                    if (resultJdn != -1) calendarFragment.bringDate(resultJdn);
                }).create();
    }
}
