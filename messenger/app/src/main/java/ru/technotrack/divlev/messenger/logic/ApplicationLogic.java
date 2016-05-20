package ru.technotrack.divlev.messenger.logic;

import android.os.HandlerThread;

import ru.technotrack.divlev.messenger.MainActivityBase;
import ru.technotrack.divlev.messenger.fragment.welcome.WelcomeLogic;

public class ApplicationLogic implements WelcomeLogic {
    private static ApplicationLogic appLogic;
    private static String TAG = ApplicationLogic.class.getSimpleName().toUpperCase();

    private MainActivityBase activity;
    private WelcomeLogic.OnNetworkConnectLister welcomeLister;

    public static ApplicationLogic instance() {
        if (appLogic == null) {
            appLogic = new ApplicationLogic();
        }
        return appLogic;
    }


    private ApplicationLogic() {}

    private class ApplicationLogicHandlerThread extends HandlerThread {

        public ApplicationLogicHandlerThread(String name) {
            super(name);
        }
    }

    public void setActivity(MainActivityBase activity) {
        this.activity = activity;
    }

    public void startApp() {

    }

    @Override
    public void setListener(OnNetworkConnectLister listener) {

    }
}
