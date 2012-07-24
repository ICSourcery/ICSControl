
package com.sourcery.icscontrol.fragments;

import net.margaritov.preference.colorpicker.ColorPickerPreference;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceScreen;
import android.provider.Settings;
import android.util.Log;

import com.sourcery.icscontrol.SettingsPreferenceFragment;
import com.sourcery.icscontrol.R;

public class StatusBarClock extends SettingsPreferenceFragment implements
        OnPreferenceChangeListener {

    private static final String PREF_ENABLE = "clock_style";
    private static final String PREF_AM_PM_STYLE = "clock_am_pm_style";
    private static final String PREF_CLOCK_WEEKDAY = "clock_weekday";
    private static final String PREF_COLOR_PICKER = "clock_color";

    ListPreference mClockStyle;
    ListPreference mClockAmPmstyle;
    ListPreference mClockWeekday;
    ColorPickerPreference mColorPicker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.prefs_statusbar_clock);

        mClockStyle = (ListPreference) findPreference(PREF_ENABLE);
        mClockStyle.setOnPreferenceChangeListener(this);
        mClockStyle.setValue(Integer.toString(Settings.System.getInt(getActivity()
                .getContentResolver(), Settings.System.STATUSBAR_CLOCK_STYLE,
                1)));

        mClockAmPmstyle = (ListPreference) findPreference(PREF_AM_PM_STYLE);
        mClockAmPmstyle.setOnPreferenceChangeListener(this);
        mClockAmPmstyle.setValue(Integer.toString(Settings.System.getInt(getActivity()
                .getContentResolver(), Settings.System.STATUSBAR_CLOCK_AM_PM_STYLE,
                2)));

        mClockWeekday = (ListPreference) findPreference(PREF_CLOCK_WEEKDAY);
        mClockWeekday.setOnPreferenceChangeListener(this);
        mClockWeekday.setValue(Integer.toString(Settings.System.getInt(getActivity()
                .getContentResolver(), Settings.System.STATUSBAR_CLOCK_WEEKDAY,
                0)));
         
       mColorPicker = (ColorPickerPreference) findPreference(PREF_COLOR_PICKER);
       mColorPicker.setOnPreferenceChangeListener(this);

    }

     @Override
     public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
            Preference preference) {
         return super.onPreferenceTreeClick(preferenceScreen, preference);
     }
 	 	
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean result = false;

        if (preference == mClockAmPmstyle) {

            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_CLOCK_AM_PM_STYLE, val);

        } else if (preference == mClockStyle) {

            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_CLOCK_STYLE, val);

       } else if (preference == mColorPicker) {
           String hex = ColorPickerPreference.convertToARGB(Integer.valueOf(String
                    .valueOf(newValue)));
 	   preference.setSummary(hex);
  	 	
            int intHex = ColorPickerPreference.convertToColorInt(hex);
            Settings.System.putInt(getActivity().getContentResolver(),
 	             Settings.System.STATUSBAR_CLOCK_COLOR, intHex);
 	    Log.e("ROMAN", intHex + "");
       } else if (preference == mClockWeekday) {
            int val = Integer.parseInt((String) newValue);
            result = Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.STATUSBAR_CLOCK_WEEKDAY, val);
        }
        return result;
    }
}
