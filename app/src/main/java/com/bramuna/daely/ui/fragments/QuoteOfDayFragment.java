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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bramuna.daely.R;
import com.bramuna.daely.data.types.QuoteData;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.ui.daely.DaelyViewModel;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuoteOfDayFragment extends Fragment {

    private final static String TAG = "QuoteOfDayFragment";

    private DaelyViewModel daelyViewModel;

    @BindView(R.id.Daely_QuoteOfDayFragment_Loading_ProgressBar)
    ProgressBar loading;
    @BindView(R.id.Daely_QuoteOfDayFragment_Content_ConstraintLayout)
    ConstraintLayout content;
    @BindView(R.id.Daely_QuoteOfDayFragment_Quote_TextView)
    TextView quote;
    @BindView(R.id.Daely_QuoteOfDayFragment_Author_TextView)
    TextView author;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        daelyViewModel = ViewModelProviders.of(getActivity()).get(DaelyViewModel.class);
        daelyViewModel.getQuoteLiveData().observe(this, this::handleResult);
        daelyViewModel.fetchQuote();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_quoteofday, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.Daely_QuoteOfDayFragment_Refresh_ImageButton)
    public void onRefreshClick(View view) {
        daelyViewModel.fetchQuote();
    }

    private void handleResult(Response<QuoteData> response) {
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

    private void showData(QuoteData data) {
        loading.setVisibility(View.GONE);
        quote.setText(data.getQuote());
        author.setText(String.format(Locale.ENGLISH, "- %s", data.getAuthor()));
        content.setVisibility(View.VISIBLE);
    }

    private void showError(Throwable error) {
        loading.setVisibility(View.GONE);
        Toast.makeText(getContext(), "An error occurred while loading the quote of the day. Sorry!", Toast.LENGTH_LONG).show();
        Log.e(TAG, error.getMessage());
    }

}
