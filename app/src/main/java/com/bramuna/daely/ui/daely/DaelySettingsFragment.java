package com.bramuna.daely.ui.daely;

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
import android.widget.Toast;

import com.bramuna.daely.R;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.data.types.WeatherData;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

public class DaelySettingsFragment extends Fragment {

    private final static String TAG = "DaelySettingsFragment";

    private DaelyViewModel daelyViewModel;

    @BindView(R.id.Daely_DaelySettingsFragment_Spinner_Spinner)
    Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daelyViewModel = ViewModelProviders.of(getActivity()).get(DaelyViewModel.class);
        daelyViewModel.getLocationSelectedIndexLiveData().observe(this, this::handleResult);
        daelyViewModel.fetchLocationSelectedIndex();
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

    private void handleResult(Response<Integer> response) {
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
    }

    private void showData(Integer data) {
        spinner.setEnabled(true);
        spinner.setSelection(data);
    }

    private void showError(Throwable error) {
        Toast.makeText(getContext(), "An error has occurred while location data. Sorry!", Toast.LENGTH_LONG).show();
        Log.e(TAG, error.getMessage());
    }
}
