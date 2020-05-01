package com.naserb.newcalendar.view.preferences;

import android.os.Bundle;

import com.naserb.newcalendar.R;
import com.naserb.newcalendar.view.dialog.preferredcalendars.CalendarPreferenceDialog;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class FragmentInterfaceCalendar extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences_interface_calendar);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference.getKey().equals("calendars_priority")) {
            new CalendarPreferenceDialog().show(getFragmentManager(), "CalendarPreferenceDialog");
            return true;
        }

        return super.onPreferenceTreeClick(preference);
    }
}
