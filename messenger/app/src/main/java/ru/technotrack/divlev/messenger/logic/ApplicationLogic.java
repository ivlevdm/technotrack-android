package ru.technotrack.divlev.messenger.logic;

import android.os.HandlerThread;

import ru.technotrack.divlev.messenger.MainActivityBase;

public class ApplicationLogic {
    private static ApplicationLogic appLogic;
    private static String TAG = ApplicationLogic.class.getSimpleName().toUpperCase();

    private MainActivityBase activity;

    public static ApplicationLogic instance() {
        if (appLogic == null) {
            appLogic = new ApplicationLogic();
        }
        return appLogic;
    }


    private ApplicationLogic() {}

    private class ApplicationLogicHandlerTread extends HandlerThread {

        public ApplicationLogicHandlerTread(String name) {
            super(name);
        }
    }

    public void setActivity(MainActivityBase activity) {
        this.activity = activity;
    }

    public void startApp() {

    }
}
