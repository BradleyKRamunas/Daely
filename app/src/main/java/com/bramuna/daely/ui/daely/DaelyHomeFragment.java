package com.bramuna.daely.ui.daely;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bramuna.daely.R;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.ui.fragments.QuoteOfDayFragment;
import com.bramuna.daely.ui.fragments.TodayInHistoryFragment;
import com.bramuna.daely.ui.fragments.WeatherFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DaelyHomeFragment extends Fragment {

    private final static String TAG = "DaelyHomeFragment";

    private FragmentManager fragmentManager;

    private final WeatherFragment weatherFragment = new WeatherFragment();
    private final TodayInHistoryFragment todayInHistoryFragment = new TodayInHistoryFragment();
    private final QuoteOfDayFragment quoteOfDayFragment = new QuoteOfDayFragment();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_home, container, false);
        fragmentManager = getFragmentManager();

        setupChildrenFragments();

        return view;
    }

    private void setupChildrenFragments() {
        fragmentManager.beginTransaction()
                .replace(R.id.Daely_DaelyHomeFragment_Holder1_FrameLayout, weatherFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.Daely_DaelyHomeFragment_Holder2_FrameLayout, todayInHistoryFragment).commit();
        fragmentManager.beginTransaction()
                .replace(R.id.Daely_DaelyHomeFragment_Holder3_FrameLayout, quoteOfDayFragment).commit();
    }

}
