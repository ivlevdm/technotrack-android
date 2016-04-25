package ru.technotrack.divlev.homework2;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TechListActivity extends AppCompatActivity implements BaseFragmentInterface {
    private static final String TAG = TechListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech_list);
    }

    public void applyFragment(BaseFragment baseFragment) {
        if (findViewById(R.id.tech_list_fragment) == null) {
            return;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.tech_list_fragment, baseFragment)
                .addToBackStack(null)
                .commit();
    }

    public void finishFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyFragment(new TechListFragment());
    }
}
