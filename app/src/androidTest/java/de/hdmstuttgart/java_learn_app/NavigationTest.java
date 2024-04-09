package de.hdmstuttgart.java_learn_app;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.navigation.ui.NavigationUI;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.java_learn_app.ui.MainActivity;
import de.hdmstuttgart.java_learn_app.ui.nav_profile.ProfileFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    public TestNavHostController navHostController;

    @Before
    public void testSetup(){
        navHostController = new TestNavHostController(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void bottomNavSwitchTest(){
        // Activity gets the testNavHostController and connects it with the bottom nav:
        activityScenarioRule.getScenario().onActivity(activity -> {
            navHostController.setGraph(R.navigation.nav_graph);
            NavigationUI.setupWithNavController((BottomNavigationView) activity.findViewById(R.id.navigationView), navHostController);
        });

        // Check if the first displayed fragment is current destination
        Assert.assertEquals(navHostController.getCurrentDestination().getId(), R.id.navigation_profile);


        onView(withId(R.id.navigation_rewards))
                .check(matches(isDisplayed()));

        onView(withId(R.id.navigation_rewards))
                .perform(click());

        // Check if new destination is the same id
        Assert.assertEquals(navHostController.getCurrentDestination().getId(), R.id.navigation_rewards);


        onView(withId(R.id.navigation_code))
                .check(matches(isDisplayed()));

        onView(withId(R.id.navigation_code))
                .perform(click());

        Assert.assertEquals(navHostController.getCurrentDestination().getId(), R.id.navigation_code);


        onView(withId(R.id.navigation_profile))
                .check(matches(isDisplayed()));

        onView(withId(R.id.navigation_profile))
                .perform(click());

        Assert.assertEquals(navHostController.getCurrentDestination().getId(), R.id.navigation_profile);
    }
}