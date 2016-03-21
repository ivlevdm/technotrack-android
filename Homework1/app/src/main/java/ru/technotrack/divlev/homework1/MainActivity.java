package ru.technotrack.divlev.homework1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
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
        private AppCompatActivity activity;

        public ListRunner(AppCompatActivity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            }

            Intent intent = new Intent(activity, ListActivity.class);
            startActivity(intent);

            activity.finish();
        }
    }
}
