package com.bramuna.daely.data.types;

public class SettingsData {

    private int selectedSpinnerPosition;
    private boolean notificationToggle;
    private String notificationTime;

    public SettingsData(int selectedSpinnerPosition, boolean notificationToggle, String notificationTime) {
        this.selectedSpinnerPosition = selectedSpinnerPosition;
        this.notificationToggle = notificationToggle;
        this.notificationTime = notificationTime;
    }

    public int getSelectedSpinnerPosition() {
        return selectedSpinnerPosition;
    }

    public boolean isNotificationToggle() {
        return notificationToggle;
    }

    public String getNotificationTime() {
        return notificationTime;
    }
}
