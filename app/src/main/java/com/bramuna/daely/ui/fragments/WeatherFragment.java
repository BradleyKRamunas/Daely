package com.bramuna.daely.ui.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
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
import com.bramuna.daely.data.types.WeatherData;
import com.bramuna.daely.ui.daely.DaelyViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherFragment extends Fragment {

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
    @BindView(R.id.Daely_WeatherFragment_Description_TextView)
    TextView description;
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

    @OnClick(R.id.Daely_WeatherFragment_Refresh_ImageButton)
    public void onRefreshClick(View view) {
        daelyViewModel.fetchWeather();
    }

    @OnClick(R.id.Daely_WeatherFragment_Yahoo_ImageView)
    public void onYahooClick(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yahoo.com/?ilc=401"));
        startActivity(intent);
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
        temperature.setText(String.format(Locale.ENGLISH, "%dºF", data.getCurrentTemp()));
        temperatureHigh.setText(String.format(Locale.ENGLISH, "%dºF", data.getMaxTemp()));
        temperatureLow.setText(String.format(Locale.ENGLISH, "%dºF", data.getMinTemp()));
        image.setImageResource(getImageResource(data.getWeatherCode()));
        description.setText(data.getWeatherState());
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
        calendar.setTimeZone(TimeZone.getDefault());
        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(calendar.getTime());
    }

    private static int getImageResource(int weatherCode) {
        switch(weatherCode) {
            case 0: return R.drawable.ic_heavyrain;
            case 1: return R.drawable.ic_heavyrain;
            case 2: return R.drawable.ic_heavyrain;
            case 3: return R.drawable.ic_thunderstorm;
            case 4: return R.drawable.ic_thunderstorm;
            case 5: return R.drawable.ic_snow;
            case 6: return R.drawable.ic_sleet;
            case 7: return R.drawable.ic_snow;
            case 8: return R.drawable.ic_hail;
            case 9: return R.drawable.ic_lightrain;
            case 10: return R.drawable.ic_hail;
            case 11: return R.drawable.ic_showers;
            case 12: return R.drawable.ic_showers;
            case 13: return R.drawable.ic_snow;
            case 14: return R.drawable.ic_snow;
            case 15: return R.drawable.ic_snow;
            case 16: return R.drawable.ic_snow;
            case 17: return R.drawable.ic_hail;
            case 18: return R.drawable.ic_sleet;
            case 19: return R.drawable.ic_heavycloud;
            case 20: return R.drawable.ic_heavycloud;
            case 21: return R.drawable.ic_heavycloud;
            case 22: return R.drawable.ic_heavycloud;
            case 23: return R.drawable.ic_heavycloud;
            case 24: return R.drawable.ic_heavycloud;
            case 25: return R.drawable.ic_heavycloud;
            case 26: return R.drawable.ic_heavycloud;
            case 27: return R.drawable.ic_heavycloud;
            case 28: return R.drawable.ic_heavycloud;
            case 29: return R.drawable.ic_lightcloud;
            case 30: return R.drawable.ic_lightcloud;
            case 31: return R.drawable.ic_clear;
            case 32: return R.drawable.ic_clear;
            case 33: return R.drawable.ic_clear;
            case 34: return R.drawable.ic_clear;
            case 35: return R.drawable.ic_hail;
            case 36: return R.drawable.ic_clear;
            case 37: return R.drawable.ic_thunderstorm;
            case 38: return R.drawable.ic_thunderstorm;
            case 39: return R.drawable.ic_thunderstorm;
            case 40: return R.drawable.ic_lightrain;
            case 41: return R.drawable.ic_snow;
            case 42: return R.drawable.ic_snow;
            case 43: return R.drawable.ic_snow;
            case 44: return R.drawable.ic_lightcloud;
            case 45: return R.drawable.ic_showers;
            case 46: return R.drawable.ic_snow;
            case 47: return R.drawable.ic_thunderstorm;
            default: return R.drawable.ic_clear;
        }
    }
}
