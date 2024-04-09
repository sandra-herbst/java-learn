package de.hdmstuttgart.java_learn_app.ui.nav_rewards;

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


import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;


public class RewardsFragment extends Fragment {

    private RewardsViewModel rewardsViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private RewardsAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rewardsViewModel = new ViewModelProvider(this).get(RewardsViewModel.class);
        userViewModel = new ViewModelProvider(getActivity()).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rewards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.reward_recyclerView);

        createRecyclerview();
        observeLiveData();
    }

    private void createRecyclerview(){
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        adapter = new RewardsAdapter(userViewModel, rewardsViewModel);
        recyclerView.setAdapter(adapter);
    }

    private void observeLiveData(){
        rewardsViewModel.getAllRewards().observe(requireActivity(), adapter::setRewards);
    }
}
