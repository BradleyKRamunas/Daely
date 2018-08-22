package com.bramuna.daely.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bramuna.daely.R;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.data.WeatherData;
import com.bramuna.daely.ui.daely.DaelyViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends Fragment implements BaseFragment {

    private final static String TAG = "WeatherFragment";

    private DaelyViewModel daelyViewModel;

    @BindView(R.id.Daely_WeatherFragment_Loading_ProgressBar)
    ProgressBar loading;
    @BindView(R.id.Daely_WeatherFragment_Content_ConstraintLayout)
    ConstraintLayout content;
    @BindView(R.id.Daely_WeatherFragment_Date_TextView)
    TextView date;
    @BindView(R.id.Daely_WeatherFragment_Location_TextView)
    TextView location;
    @BindView(R.id.Daely_WeatherFragment_Weather_ImageView)
    ImageView image;
    @BindView(R.id.Daely_WeatherFragment_Temperature_TextView)
    TextView temperature;
    @BindView(R.id.Daely_WeatherFragment_High_TextView)
    TextView temperatureHigh;
    @BindView(R.id.Daely_WeatherFragment_Low_TextView)
    TextView temperatureLow;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daelyViewModel = ViewModelProviders.of(getActivity()).get(DaelyViewModel.class);
        daelyViewModel.getWeatherLiveData().observe(this, this::handleResult);
        daelyViewModel.fetchWeather();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_weather, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void handleResult(Response<WeatherData> response) {
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
        content.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);
    }

    private void showData(WeatherData data) {
        loading.setVisibility(View.GONE);
        date.setText(getDayName(data.getLastFetchedAt()));
        location.setText(data.getLocationName());
        temperature.setText(String.format(Locale.ENGLISH, "%.0fºF", data.getCurrentTemp()));
        temperatureHigh.setText(String.format(Locale.ENGLISH, "%.0fºF", data.getMaxTemp()));
        temperatureLow.setText(String.format(Locale.ENGLISH, "%.0fºF", data.getMinTemp()));
        image.setImageResource(getImageResource(data.getWeatherState()));
        content.setVisibility(View.VISIBLE);
    }

    private void showError(Throwable error) {
        loading.setVisibility(View.GONE);
        Toast.makeText(getContext(), "An error has occurred while loading weather data. Sorry!", Toast.LENGTH_LONG).show();
        Log.e(TAG, error.getMessage());
    }

    private static String getDayName(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime());
    }

    private static int getImageResource(String weatherState) {
        switch(weatherState) {
            case "Snow": return R.drawable.ic_snow;
            case "Sleet": return R.drawable.ic_sleet;
            case "Hail": return R.drawable.ic_hail;
            case "Thunderstorm": return R.drawable.ic_thunderstorm;
            case "Heavy Rain": return R.drawable.ic_heavyrain;
            case "Light Rain": return R.drawable.ic_lightrain;
            case "Showers": return R.drawable.ic_showers;
            case "Heavy Cloud": return R.drawable.ic_heavycloud;
            case "Light Cloud": return R.drawable.ic_lightcloud;
            case "Clear": return R.drawable.ic_clear;
            default: return R.drawable.ic_clear;
        }
    }
}
