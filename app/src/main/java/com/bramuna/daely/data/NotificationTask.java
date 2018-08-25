package com.bramuna.daely.data;

public class NotificationTask extends Task {

    private boolean toggle;
    private String timeString;

    private NotificationTask() {
        super(TaskType.Notification);
    }

    public static NotificationTask turnOff() {
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.toggle = false;
        notificationTask.timeString = null;
        return notificationTask;
    }

    public static NotificationTask turnOnOrModify(String timeString) {
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.toggle = true;
        notificationTask.timeString = timeString;
        return notificationTask;
    }

    public boolean isToggle() {
        return toggle;
    }

    public String getTimeString() {
        return timeString;
    }
}
