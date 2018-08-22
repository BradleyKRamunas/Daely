package com.bramuna.daely.ui.fragments;

import android.arch.lifecycle.ViewModelProvider;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bramuna.daely.R;
import com.bramuna.daely.data.HistoryData;
import com.bramuna.daely.data.QuoteData;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.ui.daely.DaelyViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TodayInHistoryFragment extends Fragment implements BaseFragment {

    private final static String TAG = "TodayInHistoryFragment";

    private DaelyViewModel daelyViewModel;

    @BindView(R.id.Daely_TodayInHistoryFragment_Loading_ProgressBar)
    ProgressBar loading;
    @BindView(R.id.Daely_TodayInHistoryFragment_Content_ConstraintLayout)
    ConstraintLayout content;
    @BindView(R.id.Daely_TodayInHistoryFragment_Date_TextView)
    TextView date;
    @BindView(R.id.Daely_TodayInHistoryFragment_1_TextView)
    TextView firstEvent;
    @BindView(R.id.Daely_TodayInHistoryFragment_2_TextView)
    TextView secondEvent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daelyViewModel = ViewModelProviders.of(getActivity()).get(DaelyViewModel.class);
        daelyViewModel.getHistoryLiveData().observe(this, this::handleResult);
        daelyViewModel.fetchTodayInHistory();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_todayinhistory, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    private void handleResult(Response<HistoryData> response) {
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

    private void showData(HistoryData data) {
        loading.setVisibility(View.GONE);
        date.setText(String.format(Locale.ENGLISH, "Today In History - %s", data.getDate()));
        firstEvent.setText(String.format(Locale.ENGLISH, "%s - %s", data.getEventOneYear(), data.getEventOneText()));
        secondEvent.setText(String.format(Locale.ENGLISH, "%s - %s", data.getEventTwoYear(), data.getEventTwoText()));
        content.setVisibility(View.VISIBLE);
    }

    private void showError(Throwable error) {
        loading.setVisibility(View.GONE);
        Toast.makeText(getContext(), "An error occurred while loading the quote of the day. Sorry!", Toast.LENGTH_LONG).show();
        Log.e(TAG, error.getMessage());
    }
}
