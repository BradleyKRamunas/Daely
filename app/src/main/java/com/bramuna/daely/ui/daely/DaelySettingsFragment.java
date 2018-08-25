package com.bramuna.daely.ui.daely;

import android.app.TimePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bramuna.daely.R;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.data.types.SettingsData;
import com.bramuna.daely.data.types.WeatherData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DaelySettingsFragment extends Fragment {

    private final static String TAG = "DaelySettingsFragment";
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.ENGLISH);

    private DaelyViewModel daelyViewModel;

    @BindView(R.id.Daely_DaelySettingsFragment_Spinner_Spinner)
    Spinner spinner;
    @BindView(R.id.Daely_DaelySettingsFragment_Notifications_Switch)
    Switch notifications;
    @BindView(R.id.Daely_DaelySettingsFragment_Time_TextView)
    TextView time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daelyViewModel = ViewModelProviders.of(getActivity()).get(DaelyViewModel.class);
        daelyViewModel.getSettingsLiveData().observe(this, this::handleResult);
        daelyViewModel.fetchSettings();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_settings, container, false);
        ButterKnife.bind(this, view);
        spinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.location_options, android.R.layout.simple_spinner_dropdown_item));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                daelyViewModel.setLocationSelectedIndex(i).blockingAwait();
                daelyViewModel.fetchWeather();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return view;
    }

    @OnClick(R.id.Daely_DaelySettingsFragment_Time_TextView)
    public void onTimeClick(View view) {
        String setTime = time.getText().toString();
        Calendar setDate = new GregorianCalendar();
        try {
            setDate.setTime(dateFormat.parse(setTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new TimePickerDialog(getContext(), (timePicker, i, i1) -> {
            Calendar newSetDate = new GregorianCalendar();
            newSetDate.set(Calendar.HOUR_OF_DAY, i);
            newSetDate.set(Calendar.MINUTE, i1);
            String formattedText=  dateFormat.format(newSetDate.getTime());
            time.setText(formattedText);
            daelyViewModel.setNotificationTime(formattedText).blockingAwait();
        }, setDate.get(Calendar.HOUR_OF_DAY), setDate.get(Calendar.MINUTE), false).show();
    }

    @OnCheckedChanged(R.id.Daely_DaelySettingsFragment_Notifications_Switch)
    public void onToggleNotifications(boolean value) {
        if(!value) time.setEnabled(false);
        else time.setEnabled(true);
        daelyViewModel.setNotifications(value).blockingAwait();
    }

    private void handleResult(Response<SettingsData> response) {
        Status status = response.getStatus();
        switch(status) {
            case LOADING:
                showLoading();
                break;
            case COMPLETE:
                showData(response.getData());
                break;
            case ERROR:
                showError(response.getError());
                break;
        }
    }

    private void showLoading() {
        spinner.setEnabled(false);
        notifications.setEnabled(false);
        time.setEnabled(false);

    }

    private void showData(SettingsData data) {
        spinner.setEnabled(true);
        notifications.setEnabled(true);
        time.setEnabled(true);
        spinner.setSelection(data.getSelectedSpinnerPosition());
        notifications.setChecked(data.isNotificationToggle());
        time.setText(data.getNotificationTime());
    }

    private void showError(Throwable error) {
        Toast.makeText(getContext(), "An error has occurred while location data. Sorry!", Toast.LENGTH_LONG).show();
        Log.e(TAG, error.getMessage());
    }
}
