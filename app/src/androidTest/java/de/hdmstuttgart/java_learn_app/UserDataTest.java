package de.hdmstuttgart.java_learn_app;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.java_learn_app.serialize.SerializableManager;
import de.hdmstuttgart.java_learn_app.ui.MainActivity;
import de.hdmstuttgart.java_learn_app.ui.registration.UserViewModel;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
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
public class UserDataTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @BeforeClass
    public static void deleteUserData(){
        SerializableManager.deleteProgress(ApplicationProvider.getApplicationContext());
    }

    /**
     * This test only works if no user has been registered yet. If the test fails, consider
     * uninstalling the app. The deleteUserData method should clean up any user data beforehand.
     */
    @Test
    public void submitAndDisplayUserData(){
        // Dialog, submit username
        onView(withId(R.id.nameEditTxt))
                .check(matches(isDisplayed()));

        onView(withId(R.id.nameEditTxt))
                .perform(typeText("username"), closeSoftKeyboard());

        onView(withId(R.id.readyToStart))
                .check(matches(isDisplayed()));

        onView(withId(R.id.readyToStart))
                .perform(click());

        // Profile Fragment, is the correct data displayed?
        onView(withId(R.id.nameTxt))
                .check(matches(isDisplayed()));

        onView(withId(R.id.nameTxt))
                .check(matches(withText("username")));

        onView(withId(R.id.coinsTxt))
                .check(matches(isDisplayed()));

        onView(withId(R.id.coinsTxt))
                .check(matches(withText("0")));

        onView(withId(R.id.experienceStartTxt))
                .check(matches(isDisplayed()));

        onView(withId(R.id.experienceStartTxt))
                .check(matches(withText("0")));

        onView(withId(R.id.finished_challenges_rv))
                .check(matches(isDisplayed()));

        // Restart Application, is the user information still there?
        activityScenarioRule.getScenario().close();

        ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.nameTxt))
                .check(matches(isDisplayed()));

        onView(withId(R.id.nameTxt))
                .check(matches(withText("username")));

        onView(withId(R.id.coinsTxt))
                .check(matches(isDisplayed()));

        onView(withId(R.id.coinsTxt))
                .check(matches(withText("0")));

        onView(withId(R.id.experienceStartTxt))
                .check(matches(withText("0")));
    }
}