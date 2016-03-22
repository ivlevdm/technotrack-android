package ru.technotrack.divlev.homework1;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ShowListActivityAsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        asyncTask = new ShowListActivityAsyncTask();
        asyncTask.execute(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        asyncTask.cancel(true);
    }

    private void runListActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }

    private class ShowListActivityAsyncTask extends AsyncTask<MainActivity, Void, MainActivity> {
        private static final long SLEEP_TIME_SEC = 2;

        @Override
        protected MainActivity doInBackground(MainActivity... params) {
            try {
                TimeUnit.SECONDS.sleep(SLEEP_TIME_SEC);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }

            return params[0];
        }

        @Override
        protected void onPostExecute(MainActivity result) {
            result.runListActivity();
        }
    }
}
