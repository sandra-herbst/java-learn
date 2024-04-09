package de.hdmstuttgart.java_learn_app.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import de.hdmstuttgart.java_learn_app.R;
import de.hdmstuttgart.java_learn_app.serialize.User;
import de.hdmstuttgart.java_learn_app.serialize.UserHandler;

public class MainActivity extends  AppCompatActivity {

    private BottomNavigationView navigation;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpStartingFragment();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navigation = findViewById(R.id.navigationView);
        NavigationUI.setupWithNavController(navigation, navController);

        bottomNavVisibilityListener();
    }


    private void toggleBottomNav(boolean isVisible){
        if (isVisible){
            if (navigation.getVisibility() == View.GONE) navigation.setVisibility(View.VISIBLE);
        } else {
            if (navigation.getVisibility() == View.VISIBLE) navigation.setVisibility(View.GONE);
        }
    }


    private void setUpStartingFragment(){
        User user = UserHandler.getInstance(getApplicationContext()).getUser();
        Log.d("MainActivity", "setUpStartingFragment: " + user);

        // Only if username null it will start at the registration Fragment
        if (user.getUsername() == null) {

            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_profile, true)
                    .build();

            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.action_navigation_profile_to_welcomeDialogFragment, null, navOptions);
        }
    }


    private void bottomNavVisibilityListener(){
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            switch (destination.getId()){
                case R.id.navigation_code:
                case R.id.navigation_profile:
                case R.id.subtopicFragment:
                case R.id.navigation_rewards: toggleBottomNav(true); break;
                default: toggleBottomNav(false);
            }
        });
    }
}