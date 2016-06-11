package ru.technotrack.divlev.messenger;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.technotrack.divlev.messenger.fragment.BaseFragment;
import ru.technotrack.divlev.messenger.fragment.BaseFragmentActivity;
import ru.technotrack.divlev.messenger.logic.ApplicationLogic;

public class MainActivityBase extends AppCompatActivity implements BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationLogic.instance().setActivity(this);
        ApplicationLogic.instance().startApp();
    }

    @Override
    public void applyFragment(BaseFragment baseFragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.activity_fragment, baseFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void finishFragment() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ApplicationLogic.instance().startNetwork();
    }
}
