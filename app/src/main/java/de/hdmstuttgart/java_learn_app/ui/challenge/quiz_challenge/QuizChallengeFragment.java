package de.hdmstuttgart.java_learn_app.ui.challenge.quiz_challenge;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.database.entity.QuizChallengeEntity;
import de.hdmstuttgart.java_learn_app.ui.ViewModelFactory;
import de.hdmstuttgart.java_learn_app.ui.challenge.ChallengeViewModel;
import de.hdmstuttgart.java_learn_app.ui.challenge.IChallenge;
import de.hdmstuttgart.java_learn_app.ui.challenge.code_challenge.CodeChallengeFragmentDirections;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;

public class QuizChallengeFragment extends Fragment implements IChallenge {

    private static final String TAG = "QuizChallengeFragment";
    private ChallengeViewModel challengeViewModel;
    private UserViewModel userViewModel;
    private QuizChallengeEntity currentQuizChallenge;
    private QuizChallengeAdapter quizChallengeAdapter;
    private Button submitBtn, cancelQuizBtn;
    private TextView quiz_desc, quiz_title, progressTxt;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private int subtopicId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        submitBtn = view.findViewById(R.id.quiz_submitBtn);
        recyclerView = view.findViewById(R.id.quiz_recyclerView);
        quiz_desc = view.findViewById(R.id.quiz_desc);
        quiz_title = view.findViewById(R.id.quiz_title);
        progressTxt = view.findViewById(R.id.quiz_progressTxt);
        cancelQuizBtn = view.findViewById(R.id.cancelQuizBtn);
        progressBar = view.findViewById(R.id.quiz_progressBar);

        subtopicId = QuizChallengeFragmentArgs.fromBundle(getArguments()).getSubtopicId();
        NavBackStackEntry backStackEntry = Navigation.findNavController(view).getBackStackEntry(R.id.challenge_graph);
        challengeViewModel = new ViewModelProvider(backStackEntry, new ViewModelFactory(subtopicId, getActivity().getApplication())).get(ChallengeViewModel.class);

        currentQuizChallenge = challengeViewModel.getFirstQuizChallenge();

        prepareLayout();
        createRecyclerView();
        onChallengeCheck();
    }

    private void prepareLayout(){
        double progress = ((double) challengeViewModel.getCurrentChallengePosition() / (double)challengeViewModel.getTotalChallengeCount())*100;
        progressBar.setProgress((int) progress, true);
        quiz_desc.setText(currentQuizChallenge.description);
        quiz_title.setText(currentQuizChallenge.title);
        progressTxt.setText(String.format(getString(R.string.progressTxt), String.valueOf(challengeViewModel.getCurrentChallengePosition()), String.valueOf(challengeViewModel.getTotalChallengeCount())));

        cancelQuizBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle(android.R.string.cancel)
                    .setMessage(R.string.cancelChallenge)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(QuizChallengeFragmentDirections.actionQuizChallengeFragmentToNavigationCode());
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });
    }

    private void createRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        quizChallengeAdapter = new QuizChallengeAdapter(currentQuizChallenge.answers, currentQuizChallenge.solutionPosition);
        recyclerView.setAdapter(quizChallengeAdapter);
    }

    private void addViewListener(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        switch (challengeViewModel.getNextChallengeType()){
            case CODE:
                submitBtn.setOnClickListener(v -> { navController.navigate(QuizChallengeFragmentDirections.actionQuizChallengeFragmentToCodeChallengeFragment(subtopicId));});
                break;
            case QUIZ:
                submitBtn.setOnClickListener(v -> { navController.navigate(QuizChallengeFragmentDirections.actionQuizChallengeFragmentSelf2(subtopicId));});
                break;
            case END:
                submitBtn.setOnClickListener(v -> { navController.navigate(QuizChallengeFragmentDirections.actionQuizChallengeFragmentToChallengeEndFragment(subtopicId));});
                break;
        }
    }

    private void changeViewholderStyle(boolean hasSolved){
        QuizChallengeAdapter.AnswersViewHolder viewHolder = (QuizChallengeAdapter.AnswersViewHolder) recyclerView.findViewHolderForAdapterPosition(quizChallengeAdapter.getCheckedPosition());

        if (hasSolved) viewHolder.quiz_layout_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.quiz_right));
        else viewHolder.quiz_layout_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.quiz_wrong));
    }

    @Override
    public void onChallengeCheck() {
        submitBtn.setOnClickListener(v -> {
            try {
                if (quizChallengeAdapter.hasSolutionChecked()) onChallengeSolved();
                else onChallengeFailed();
            } catch (NullPointerException e){
                Toast.makeText(getContext(), "You must decide on an option here before continuing!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChallengeSolved() {
        changeViewholderStyle(true);

        if (!currentQuizChallenge.isCompleted){
            challengeViewModel.addCoins(currentQuizChallenge.difficulty);
            challengeViewModel.addExperience(currentQuizChallenge.difficulty*3);
            userViewModel.addCoins(currentQuizChallenge.difficulty);
            userViewModel.addExperience(currentQuizChallenge.difficulty*3);

            currentQuizChallenge.setCompleted(true);
            Log.d(TAG, "onChallengeSolved");
            challengeViewModel.updateQuizChallenge(currentQuizChallenge);

        } else {
            Toast.makeText(getContext(), "Challenge has already been solved, no rewards", Toast.LENGTH_SHORT).show();
        }

        onChallengeNext();
    }

    @Override
    public void onChallengeFailed() {
        changeViewholderStyle(false);

        QuizChallengeAdapter.AnswersViewHolder viewHolder = (QuizChallengeAdapter.AnswersViewHolder) recyclerView.findViewHolderForAdapterPosition(currentQuizChallenge.solutionPosition);
        viewHolder.quiz_layout_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.quiz_right));
        challengeViewModel.increaseTotalFailCount();
        Log.d(TAG, "onChallengeFailed: RETRY");

        submitBtn.setOnClickListener(null);
        onChallengeNext();
    }

    @Override
    public void onChallengeNext() {
        submitBtn.setOnClickListener(null);
        submitBtn.setText(">");
        challengeViewModel.removeFirstQuizChallenge();
        addViewListener();
    }
}