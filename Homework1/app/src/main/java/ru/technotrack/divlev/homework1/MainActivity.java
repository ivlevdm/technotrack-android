package ru.technotrack.divlev.homework1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ListRunner listRunner = new ListRunner(this);
        Thread thread = new Thread(listRunner);
        thread.start();
    }

    class ListRunner implements Runnable {
        private static final long SLEEP_TIME = 2000;

        private Activity activity;

        public ListRunner(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            long delta = 0;

            while (delta < SLEEP_TIME) {
                try {
                    Thread.sleep(SLEEP_TIME - delta);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
                delta = System.currentTimeMillis() - startTime;
            }

            if (!activity.isFinishing()) {
                Intent intent = new Intent(activity, ListActivity.class);
                startActivity(intent);

                activity.finish();
            }
        }
    }
}
