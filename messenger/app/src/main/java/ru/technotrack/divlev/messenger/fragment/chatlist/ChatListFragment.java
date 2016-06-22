package ru.technotrack.divlev.messenger.fragment.chatlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.List;

import ru.technotrack.divlev.messenger.R;
import ru.technotrack.divlev.messenger.fragment.BaseFragment;

public class ChatListFragment extends BaseFragment implements ChatListView {
    private ChatListPresenter presenter;
    private ListView listView;
    private ProgressBar progressBar;

    public ChatListFragment() {
        presenter = new ChatListPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.chatlist_fragment, container, false);
        return result;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) findViewById(R.id.list);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        presenter.uploadChatList();
    }

    @Override
    public void setItems(List<String> items) {
        listView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, items));
    }
}
