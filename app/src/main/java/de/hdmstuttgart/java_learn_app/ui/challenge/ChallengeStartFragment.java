package de.hdmstuttgart.java_learn_app.ui.challenge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.ui.ViewModelFactory;
import de.hdmstuttgart.java_learn_app.ui.nav_code.subtopic.SubtopicViewModel;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;

public class ChallengeStartFragment extends Fragment {

    private ChallengeViewModel challengeViewModel;
    private int subtopicId;
    private TextView challengeCountTxt, subtopic_titleStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subtopicId = ChallengeStartFragmentArgs.fromBundle(getArguments()).getSubtopicId();
        challengeCountTxt = view.findViewById(R.id.challengeCountTxt);
        subtopic_titleStart = view.findViewById(R.id.subtopic_titleStart);
        
        Log.d("TAG", "onViewCreated: " + subtopicId);

        NavBackStackEntry backStackEntry = Navigation.findNavController(view).getBackStackEntry(R.id.challenge_graph);
        challengeViewModel = new ViewModelProvider(backStackEntry, new ViewModelFactory(subtopicId, getActivity().getApplication())).get(ChallengeViewModel.class);

        prepareLayout();
        addViewListener(view.findViewById(R.id.startBtn));
    }


    private void prepareLayout(){
        subtopic_titleStart.setText(String.format(getString(R.string.title_start), challengeViewModel.getNextChallengeType().name()));
        challengeCountTxt.setText((String.format(getString(R.string.challengeCount), String.valueOf(challengeViewModel.getTotalChallengeCount()))));
    }

    private void addViewListener(Button button){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        switch (challengeViewModel.getNextChallengeType()){
            case CODE:
                button.setOnClickListener(v -> { navController.navigate(ChallengeStartFragmentDirections.actionChallengeStartFragmentToCodeChallengeFragment(subtopicId));});
                break;
            case QUIZ:
                button.setOnClickListener(v -> { navController.navigate(ChallengeStartFragmentDirections.actionChallengeStartFragmentToQuizChallengeFragment(subtopicId));});
                break;
            default: Log.e("TAG", "addViewListener: no challenge registered for this subtopic just yet");
        }
    }
}