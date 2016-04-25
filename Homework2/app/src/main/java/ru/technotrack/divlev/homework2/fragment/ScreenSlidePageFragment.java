package ru.technotrack.divlev.homework2.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.technotrack.divlev.homework2.R;
import ru.technotrack.divlev.homework2.utils.TechnologyDescription;

public class ScreenSlidePageFragment extends BaseFragment {
    private TechnologyDescription desc;

    public static ScreenSlidePageFragment create(TechnologyDescription desc) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        fragment.desc = desc;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.pager_item, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TextView tv = (TextView)findViewById(R.id.dict_word);
        tv.setText(desc.getTitle());
    }
}
