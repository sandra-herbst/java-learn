package de.hdmstuttgart.java_learn_app.ui.nav_profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.ui.nav_code.FinishedSubtopicsViewModel;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;
import de.hdmstuttgart.java_learn_app.util.Level;


public class ProfileFragment extends Fragment {

    private static final String TAG = "ProfileFragment";
    private FinishedSubtopicsViewModel finishedSubtopicsViewModel;
    private UserViewModel userViewModel;
    private TextView nameTxt, coinsTxt, levelTitleTxt, experienceStartTxt, experienceEndTxt;
    private ProgressBar experienceProgressBar;
    private RecyclerView recyclerView;
    private FinishedChallengeAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        this.finishedSubtopicsViewModel = new ViewModelProvider(requireActivity()).get(FinishedSubtopicsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameTxt = view.findViewById(R.id.nameTxt);
        coinsTxt = view.findViewById(R.id.coinsTxt);
        levelTitleTxt = view.findViewById(R.id.levelTitleTxt);
        recyclerView = view.findViewById(R.id.finished_challenges_rv);
        experienceProgressBar = view.findViewById(R.id.experienceProgressBar);
        experienceStartTxt = view.findViewById(R.id.experienceStartTxt);
        experienceEndTxt = view.findViewById(R.id.experienceEndTxt);

        createRecyclerview();
        observeUserData();
    }

    private void observeUserData(){
        userViewModel.getUsernameLiveData().observe(requireActivity(), username -> {
            Log.d(TAG, "observeUserData: Username has been updated - " + username);
            nameTxt.setText(username);
        });

        userViewModel.getUserCoinLiveData().observe(requireActivity(), coinAmount -> {
            Log.d(TAG, "observeUserData: Coins have been updated - " + coinAmount);
            coinsTxt.setText(String.valueOf(coinAmount));
        });

        userViewModel.getUserExpLiveData().observe(requireActivity(), userExp -> {
            Log.d(TAG, "observeUserData: Exp have been updated - " + userExp);
            Level level = Level.getLevel(userExp);
            levelTitleTxt.setText(level.getTitle());
            experienceStartTxt.setText(String.valueOf(userExp));
            experienceEndTxt.setText(String.valueOf(level.getEXP_END()));
            experienceProgressBar.setProgress(level.getProgressPercentage(userExp), true);
        });

        finishedSubtopicsViewModel.getCompletedSubtopicTitles().observe(requireActivity(), subtopicTitles -> {
            Log.d(TAG, "onChanged in Profile fragment : Livedata of completed subtopics has changed!");
            adapter.setCompletedSubtopics(subtopicTitles);
        });
    }

    private void createRecyclerview(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        adapter = new FinishedChallengeAdapter();
        recyclerView.setAdapter(adapter);
    }
}