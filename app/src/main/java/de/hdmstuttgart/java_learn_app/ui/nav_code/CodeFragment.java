package de.hdmstuttgart.java_learn_app.ui.nav_code;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.ui.nav_code.topic.TopicAdapter;
import de.hdmstuttgart.java_learn_app.ui.nav_code.topic.TopicViewModel;

public class CodeFragment extends Fragment {

    private TopicViewModel topicViewModel;
    private RecyclerView recyclerView;
    private TopicAdapter adapter;
    private SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.topic_recyclerView);
        searchView = view.findViewById(R.id.searchTopicSearchView);

        createRecyclerview();
        addSearchViewFilter();
        observeLiveData();
    }

    private void observeLiveData() {
        topicViewModel.getAllTopics().observe(requireActivity(), topicEntities -> {
            adapter.setTopics(topicEntities);
        });
    }

    private void createRecyclerview(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        adapter = new TopicAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void addSearchViewFilter(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
}