package de.hdmstuttgart.java_learn_app.ui.challenge;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.ui.ViewModelFactory;
import de.hdmstuttgart.java_learn_app.ui.nav_code.subtopic.SubtopicViewModel;


public class ChallengeEndFragment extends Fragment {

    private ChallengeViewModel challengeViewModel;
    private Button endOfChallengeBtn;
    private int subtopicId;
    private TextView failCountTxt, expText, coinsTxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_challenge_end, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        endOfChallengeBtn = view.findViewById(R.id.endOfChallengeBtn);
        failCountTxt = view.findViewById(R.id.failCountTxt);
        expText = view.findViewById(R.id.expTxt);
        coinsTxt = view.findViewById(R.id.coinsTxt);

        subtopicId = ChallengeEndFragmentArgs.fromBundle(getArguments()).getSubtopicId();
        NavBackStackEntry backStackEntry = Navigation.findNavController(view).getBackStackEntry(R.id.challenge_graph);

        challengeViewModel = new ViewModelProvider(backStackEntry, new ViewModelFactory(subtopicId, getActivity().getApplication())).get(ChallengeViewModel.class);
        // Set Subtopic completed
        challengeViewModel.updateSubtopicCompleted();

        addViewListener();
        prepareLayout();
    }

    private void prepareLayout(){
        failCountTxt.setText(String.format(getString(R.string.failCount), Integer.toString(challengeViewModel.getTotalFailCount())));
        expText.setText(String.format(getString(R.string.experience), Integer.toString(challengeViewModel.getExperience())));
        coinsTxt.setText(String.format(getString(R.string.coins), Integer.toString(challengeViewModel.getCoins())));
    }


    private void addViewListener(){
        endOfChallengeBtn.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            navController.navigate(ChallengeEndFragmentDirections.actionChallengeEndFragmentToNavigationCode());
        });
    }
}