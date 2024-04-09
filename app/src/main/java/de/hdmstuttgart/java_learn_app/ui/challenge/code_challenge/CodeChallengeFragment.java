package de.hdmstuttgart.java_learn_app.ui.challenge.code_challenge;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.database.entity.CodeChallengeEntity;
import de.hdmstuttgart.java_learn_app.networking.CompilerRequest;
import de.hdmstuttgart.java_learn_app.networking.MethodType;
import de.hdmstuttgart.java_learn_app.ui.ViewModelFactory;
import de.hdmstuttgart.java_learn_app.ui.challenge.ChallengeViewModel;
import de.hdmstuttgart.java_learn_app.ui.challenge.IChallenge;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;


public class CodeChallengeFragment extends Fragment implements IChallenge {

    private static final String TAG = "CodeChallengeFragment";
    private ChallengeViewModel challengeViewModel;
    private UserViewModel userViewModel;
    private CodeChallengeViewModel codeChallengeViewModel;
    private CodeChallengeEntity currentCodeChallenge;
    private Button nextBtn, cancelCodeBtn;
    private String methodDefinition;
    private TextView code_desc, code_title, code_progressTxt, code_classname, methodDefinitionTxt, methodEndTxt, code_outputTxt;
    private ProgressBar progressBar;
    private EditText code_userInputTxt;
    private int subtopicId;
    private LinearLayout layout;
    private ProgressBar spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code_challenge, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextBtn = view.findViewById(R.id.code_submitBtn);
        cancelCodeBtn = view.findViewById(R.id.cancelCodeBtn);
        code_desc = view.findViewById(R.id.code_desc);
        code_title = view.findViewById(R.id.code_title);
        code_progressTxt = view.findViewById(R.id.code_progressTxt);
        progressBar = view.findViewById(R.id.code_progressBar);
        code_classname = view.findViewById(R.id.code_classname);
        methodDefinitionTxt = view.findViewById(R.id.methodDefinitionTxt);
        methodEndTxt = view.findViewById(R.id.methodEndTxt);
        layout = view.findViewById(R.id.code_rootLayout);
        code_userInputTxt = view.findViewById(R.id.code_userInputTxt);
        code_outputTxt = view.findViewById(R.id.code_outputTxt);
        spinner = view.findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);

        subtopicId = CodeChallengeFragmentArgs.fromBundle(getArguments()).getSubtopicId();
        NavBackStackEntry backStackEntry = Navigation.findNavController(view).getBackStackEntry(R.id.challenge_graph);
        challengeViewModel = new ViewModelProvider(backStackEntry, new ViewModelFactory(subtopicId, getActivity().getApplication())).get(ChallengeViewModel.class);

        currentCodeChallenge = challengeViewModel.getFirstCodeChallenge();

        codeChallengeViewModel = new ViewModelProvider(this).get(CodeChallengeViewModel.class);

        prepareLayout();
        onChallengeCheck();
    }

    private void prepareLayout(){
        if (currentCodeChallenge.methodName == null){
            layout.removeView(methodDefinitionTxt);
            layout.removeView(methodEndTxt);
            Log.e("TAG", "prepareLayout: remove method def");
        } else {
            methodDefinition = currentCodeChallenge.accessModifier + " " + currentCodeChallenge.methodType + " " + currentCodeChallenge.methodName + "() {";
            methodDefinitionTxt.setText(methodDefinition);
        }

        double progress = ((double) challengeViewModel.getCurrentChallengePosition() / (double)challengeViewModel.getTotalChallengeCount())*100;
        progressBar.setProgress((int) progress, true);

        code_desc.setText(currentCodeChallenge.description);
        code_title.setText(currentCodeChallenge.title);
        code_progressTxt.setText(String.format(getString(R.string.progressTxt), String.valueOf(challengeViewModel.getCurrentChallengePosition()), String.valueOf(challengeViewModel.getTotalChallengeCount())));

        cancelCodeBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext())
                    .setTitle(android.R.string.cancel)
                    .setMessage(R.string.cancelChallenge)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                        navController.navigate(CodeChallengeFragmentDirections.actionCodeChallengeFragmentToNavigationCode());
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });
    }

    private void showTextResult(boolean isSuccessful, String text){
        if (isSuccessful){
            code_outputTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.success_green));
        } else {
            code_outputTxt.setTextColor(ContextCompat.getColor(getContext(), R.color.fail_red));
        }
        code_outputTxt.setText(text);
    }

    private void addNextChallengeListener(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        switch (challengeViewModel.getNextChallengeType()){
            case CODE:
                nextBtn.setOnClickListener(v -> { navController.navigate(CodeChallengeFragmentDirections.actionCodeChallengeFragmentSelf(subtopicId));});
                break;
            case QUIZ:
                nextBtn.setOnClickListener(v -> { navController.navigate(CodeChallengeFragmentDirections.actionCodeChallengeFragmentToQuizChallengeFragment(subtopicId));});
                break;
            case END:
                nextBtn.setOnClickListener(v -> { navController.navigate(CodeChallengeFragmentDirections.actionCodeChallengeFragmentToChallengeEndFragment(subtopicId));});
                break;
        }
    }

    private CompilerRequest createRequestBody(){
        // TODO: 11.05.2021 Check if user has entered the right method name
        return new CompilerRequest(methodDefinition + code_userInputTxt.getText().toString() + "}", currentCodeChallenge.methodName);
    }

    @Override
    public void onChallengeCheck() {
        codeChallengeViewModel.getCompilerResponseLiveData().observe(getViewLifecycleOwner(), compilerResponse -> {
            if (compilerResponse != null) {
                if (compilerResponse.getResult() != null){
                    spinner.setVisibility(View.INVISIBLE);
                    if(compilerResponse.getResult().equals(currentCodeChallenge.solution)) onChallengeSolved();
                    else onChallengeFailed();
                }

                if (compilerResponse.getError() != null){
                    spinner.setVisibility(View.INVISIBLE);
                    showTextResult(false, compilerResponse.getError());
                }
            }
        });

        nextBtn.setOnClickListener(v -> {
            try {
                spinner.setVisibility(View.VISIBLE);
                if (!code_userInputTxt.getText().toString().equals("")){
                    codeChallengeViewModel.sendCode(MethodType.INTEGER, createRequestBody());
                } else {
                    spinner.setVisibility(View.INVISIBLE);
                    showTextResult(false, getString(R.string.emptyInputError));
                }
                
            } catch (Exception e){
                spinner.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "Couldnt reach server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChallengeSolved() {
        if (!currentCodeChallenge.isCompleted){
            showTextResult(true, getString(R.string.success));

            challengeViewModel.addCoins(currentCodeChallenge.difficulty);
            challengeViewModel.addExperience(currentCodeChallenge.difficulty*4);
            userViewModel.addCoins(currentCodeChallenge.difficulty);
            userViewModel.addExperience(currentCodeChallenge.difficulty*4);

            currentCodeChallenge.setCompleted(true);
            challengeViewModel.updateCodeChallenge(currentCodeChallenge);

        } else {
            Toast.makeText(getContext(), "Challenge has already been solved, no rewards", Toast.LENGTH_SHORT).show();
        }

        onChallengeNext();
    }

    @Override
    public void onChallengeFailed() {
        Log.d(TAG ,"onChallengeFailed: RETRY");
        showTextResult(false, "Your code has compiled, but has not the desired result.");
        challengeViewModel.increaseTotalFailCount();
    }

    @Override
    public void onChallengeNext() {
        nextBtn.setText(">");
        challengeViewModel.removeFirstCodeChallenge();
        addNextChallengeListener();
    }
}