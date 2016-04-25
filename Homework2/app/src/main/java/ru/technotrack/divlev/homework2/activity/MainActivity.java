package ru.technotrack.divlev.homework2.activity;

import android.app.*;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.technotrack.divlev.homework2.utils.HttpDownloader;
import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;

public class MainActivity extends Activity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static boolean isTaskRun = false;
    private static boolean isJsonParsed = false;
    private static boolean isTimerOver = false;

    private SleepAsyncTask sleepAsyncTask;
    private DownloadAndParseJsonAsyncTask jsonAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isTaskRun) {
            isTaskRun = true;
            isTimerOver= false;
            isJsonParsed = false;

            sleepAsyncTask = new SleepAsyncTask(this);
            sleepAsyncTask.execute();

            jsonAsyncTask = new DownloadAndParseJsonAsyncTask(this);
            jsonAsyncTask.execute();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!isChangingConfigurations()) {
            if (sleepAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                sleepAsyncTask.cancel(true);
            }
            if (jsonAsyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                jsonAsyncTask.cancel(true);
            }
            isTaskRun = false;
        }
    }

    private void runListActivity() {
        if (isJsonParsed && isTimerOver) {
            if (TechnologyDescription.getData() != null) {
                Intent intent = new Intent(getApplicationContext(), TechListActivity.class);
                startActivity(intent);
            } else {
                Log.i(TAG, "Technology description is not downloaded.");
            }
            finish();
        }
    }

    private class SleepAsyncTask extends AsyncTask<Void, Void, Void> {
        private static final long SLEEP_TIME_SEC = 2;

        private MainActivity activity;

        public SleepAsyncTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                TimeUnit.SECONDS.sleep(SLEEP_TIME_SEC);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            isTimerOver = true;
            activity.runListActivity();
        }
    }

    private class DownloadAndParseJsonAsyncTask extends AsyncTask<Void, Void, Void> {
        final String TAG = DownloadAndParseJsonAsyncTask.class.getSimpleName();

        private MainActivity activity;

        public DownloadAndParseJsonAsyncTask(MainActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String jsonString = HttpDownloader.getUrlString(getString(R.string.json_url));
                List<TechnologyDescription> data =
                        TechnologyDescription.parseFromJson(getApplicationContext(), jsonString);
                TechnologyDescription.initData(data);
                //Log.d(TAG, jsonString);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            isJsonParsed = true;
            activity.runListActivity();
        }
    }
}
