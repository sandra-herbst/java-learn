package de.hdmstuttgart.java_learn_app.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.util.InvalidInputException;

public class WelcomeDialogFragment extends DialogFragment {

    private Button btn;
    private EditText nameEditText;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.readyToStart);
        nameEditText = view.findViewById(R.id.nameEditTxt);

        addViewListener();
    }


    private void addViewListener(){
        btn.setOnClickListener(click ->{
            try {
                String username = nameEditText.getText().toString();
                userViewModel.setUsername(username);
                Toast.makeText(getContext(), "Hello " + username + "!", Toast.LENGTH_LONG).show();
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_welcomeFragment_to_navigation_profile);
            } catch (InvalidInputException e) {
                Toast.makeText(getContext(), "Your name must contain at least 3 letters.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}