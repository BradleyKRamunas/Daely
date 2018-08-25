package com.bramuna.daely.ui.daely;

import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.bramuna.daely.R;
import com.bramuna.daely.data.NotificationTask;
import com.bramuna.daely.data.Response;
import com.bramuna.daely.data.Status;
import com.bramuna.daely.data.Task;
import com.bramuna.daely.data.TaskType;
import com.bramuna.daely.util.NotificationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaelyActivity extends AppCompatActivity {

    private final static String TAG = "DaelyActivity";

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final DaelyHomeFragment homeFragment= new DaelyHomeFragment();
    private final DaelySettingsFragment settingsFragment = new DaelySettingsFragment();
    private Fragment activeFragment = homeFragment;

    private DaelyViewModel daelyViewModel;

    @BindView(R.id.Daely_MainActivity_NavBar_BottomNavigationView)
    BottomNavigationView navigationBar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.Daely_MainActivity_Home_NavigationMenuButton:
                        if(activeFragment != homeFragment) {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.home_enter, R.anim.settings_exit)
                                    .hide(activeFragment)
                                    .show(homeFragment)
                                    .commit();
                            activeFragment = homeFragment;
                        }
                        return true;
                    case R.id.Daely_MainActivity_Settings_NavigationMenuButton:
                        if(activeFragment != settingsFragment) {
                            fragmentManager.beginTransaction()
                                    .setCustomAnimations(R.anim.settings_enter, R.anim.home_exit)
                                    .hide(activeFragment)
                                    .show(settingsFragment)
                                    .commit();
                            activeFragment = settingsFragment;
                        }
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_daely);

        ButterKnife.bind(this);

        navigationBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction()
                .add(R.id.Daely_MainActivity_FragmentHolder_FrameLayout, settingsFragment).hide(settingsFragment).commit();
        fragmentManager.beginTransaction()
                .add(R.id.Daely_MainActivity_FragmentHolder_FrameLayout, homeFragment).commit();

        daelyViewModel = ViewModelProviders.of(this).get(DaelyViewModel.class);
        daelyViewModel.getTasksLiveData().observe(this, this::handleTask);

    }

    private void handleTask(Task task) {
        if(task.getType() == TaskType.Notification) {
            NotificationTask notificationTask = (NotificationTask) task;
            if(notificationTask.isToggle()) {
                // enable or update notification
                NotificationUtils.updateNotification(getApplicationContext(), notificationTask.getTimeString());
            } else {
                // disable notification
                NotificationUtils.cancelNotification(getApplicationContext());
            }
        }
    }

}
