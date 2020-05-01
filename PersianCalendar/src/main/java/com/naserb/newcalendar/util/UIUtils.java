package com.naserb.newcalendar.util;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import com.naserb.newcalendar.Constants;
import com.naserb.newcalendar.R;
import com.naserb.newcalendar.entity.DeviceCalendarEvent;
import com.github.praytimes.Clock;

import java.util.Locale;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class UIUtils {
    static public void setActivityTitleAndSubtitle(Activity activity, String title, String subtitle) {
        //noinspection ConstantConditions
        ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(title);
            supportActionBar.setSubtitle(subtitle);
        }
    }

    public static void askForCalendarPermission(Activity activity) {
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{
                            Manifest.permission.READ_CALENDAR
                    },
                    Constants.CALENDAR_READ_PERMISSION_REQUEST_CODE);
        }
    }

    public static void askForLocationPermission(Activity activity) {
        if (activity != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    Constants.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public static void toggleShowCalendarOnPreference(Context context, boolean enable) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(Constants.PREF_SHOW_DEVICE_CALENDAR_EVENTS, enable);
        edit.apply();
    }

    static public String formatDeviceCalendarEventTitle(DeviceCalendarEvent event) {
        String desc = event.getDescription();
        String title = event.getTitle();
        if (!TextUtils.isEmpty(desc))
            title += " (" + Html.fromHtml(event.getDescription()).toString().trim() + ")";

        return title.replaceAll("\\n", " ").trim();
    }

    public static String baseClockToString(Clock clock) {
        return baseClockToString(clock.getHour(), clock.getMinute());
    }


    public static String baseClockToString(int hour, int minute) {
        return Utils.formatNumber(String.format(Locale.ENGLISH, "%d:%02d", hour, minute));
    }

    public static boolean isRTL(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return context.getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
        }
        return false;
    }

    static public String getFormattedClock(Clock clock) {
        String timeText = null;

        int hour = clock.getHour();
        if (!Utils.isClockIn24()) {
            if (hour >= 12) {
                timeText = Utils.getAppLanguage().equals(Constants.LANG_CKB) ? Constants.PM_IN_CKB : Constants.PM_IN_PERSIAN;
                hour -= 12;
            } else {
                timeText = Utils.getAppLanguage().equals(Constants.LANG_CKB) ? Constants.AM_IN_CKB : Constants.AM_IN_PERSIAN;
            }
        }

        String result = baseClockToString(hour, clock.getMinute());
        if (!Utils.isClockIn24()) {
            result = result + " " + timeText;
        }
        return result;
    }

    static public @StringRes
    int getPrayTimeText(String athanKey) {
        switch (athanKey) {
            case "FAJR":
                return R.string.azan1;

            case "DHUHR":
                return R.string.azan2;

            case "ASR":
                return R.string.azan3;

            case "MAGHRIB":
                return R.string.azan4;

            case "ISHA":
            default:
                return R.string.azan5;
        }
    }

    static public @DrawableRes
    int getPrayTimeImage(String athanKey) {
        switch (athanKey) {
            case "FAJR":
                return R.drawable.fajr;

            case "DHUHR":
                return R.drawable.dhuhr;

            case "ASR":
                return R.drawable.asr;

            case "MAGHRIB":
                return R.drawable.maghrib;

            case "ISHA":
            default:
                return R.drawable.isha;
        }
    }

    @StyleRes
    public static int getThemeFromName(String name) {
        switch (name) {
            case Constants.DARK_THEME:
                return R.style.DarkTheme;

            case Constants.CLASSIC_THEME:
                return R.style.ClassicTheme;

            case Constants.BLUE_THEME:
                return R.style.BlueTheme;

            default:
            case Constants.LIGHT_THEME:
                return R.style.LightTheme;
        }
    }

    // https://stackoverflow.com/a/27788209
    private static Uri resourceToUri(Context context, int resID) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                context.getResources().getResourcePackageName(resID) + '/' +
                context.getResources().getResourceTypeName(resID) + '/' +
                context.getResources().getResourceEntryName(resID));
    }

    static public Uri getDefaultAthanUri(Context context) {
        return resourceToUri(context, R.raw.abdulbasit);
    }

    static String getOnlyLanguage(String string) {
        return string.replaceAll("-(IR|AF|US)", "");
    }
}
