package de.hdmstuttgart.java_learn_app.ui.nav_code.subtopic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.database.ThreadExecutor;
import de.hdmstuttgart.java_learn_app.database.repository.SubtopicRepository;
import de.hdmstuttgart.java_learn_app.ui.ViewModelFactory;

public class SubtopicFragment extends Fragment {

    private SubtopicViewModel subtopicViewModel;
    private RecyclerView recyclerView;
    private TextView titleDetail;
    private TextView descDetail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subtopic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        titleDetail = view.findViewById(R.id.titleDetailText);
        descDetail = view.findViewById(R.id.descDetailText);
        recyclerView = view.findViewById(R.id.subtopic_recyclerView);

        int topicId = SubtopicFragmentArgs.fromBundle(getArguments()).getTopicId();

        subtopicViewModel = new ViewModelProvider(this, new ViewModelFactory(topicId, getActivity().getApplication())).get(SubtopicViewModel.class);

        prepareTransition();
        createRecyclerView();
    }

    private void createRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        SubtopicAdapter adapter = new SubtopicAdapter(subtopicViewModel);
        recyclerView.setAdapter(adapter);

        observeLiveData(adapter);
    }

    private void observeLiveData(SubtopicAdapter adapter){
        subtopicViewModel.getAllSubtopicsOfTopic().observe(requireActivity(), subtopicEntities -> {
            Log.d("CodeFragment", "onChanged: Livedata of Topics has changed!");
            adapter.setSubtopics(subtopicEntities);
        });
    }

    /**
     * prepare the transition animation of shared elements (From CodeFragment to SubtopicFragment)
     */
    private void prepareTransition(){
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.explode));
        postponeEnterTransition();

        String title = SubtopicFragmentArgs.fromBundle(getArguments()).getTitleTransitionName();
        String desc = SubtopicFragmentArgs.fromBundle(getArguments()).getDescTransitionName();

        titleDetail.setTransitionName(title);
        titleDetail.setText(title);
        descDetail.setTransitionName(desc);
        descDetail.setText(desc);

        startPostponedEnterTransition();
    }

}