package de.hdmstuttgart.java_learn_app;

import android.content.Context;
import android.content.res.Resources;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.navigation.Navigation;
import androidx.navigation.testing.TestNavHostController;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.hdmstuttgart.java_learn_app.ui.MainActivity;
import de.hdmstuttgart.java_learn_app.ui.nav_code.CodeFragment;
import de.hdmstuttgart.java_learn_app.ui.nav_profile.ProfileFragment;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.guava.base.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class CodeFragmentTest {

    FragmentScenario<CodeFragment> codeFragmentScenario;

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void testSetup(){
        codeFragmentScenario = FragmentScenario.launchInContainer(CodeFragment.class);
    }

    @Test
    public void codeElementsExist(){
        onView(withId(R.id.topic_recyclerView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.topic_recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Introduction")))));

        onView(withId(R.id.topic_recyclerView))
                .check(matches(atPosition(5, hasDescendant(withText("Topic 6")))));
    }

    @Test
    public void searchViewFilter(){
        onView(withId(R.id.searchTopicSearchView))
                .check(matches(isDisplayed()));

        onView(withId(R.id.searchTopicSearchView))
                .perform(typeSearchViewText("6"), closeSoftKeyboard());

        onView(withId(R.id.topic_recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Topic 6")))));

        onView(withId(R.id.searchTopicSearchView))
                .perform(typeSearchViewText("5"), closeSoftKeyboard());

        onView(withId(R.id.topic_recyclerView))
                .check(matches(atPosition(0, hasDescendant(withText("Topic 5")))));

        onView(withId(R.id.searchTopicSearchView))
                .perform(typeSearchViewText(""), closeSoftKeyboard());
    }

    @Test
    public void challengesRVTest(){
        codeFragmentScenario.onFragment( codeFragment -> {

            RecyclerView recyclerView = codeFragment.getView().findViewById(R.id.topic_recyclerView);

            Assert.assertEquals(6, recyclerView.getAdapter().getItemCount());
        });
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    public static ViewAction typeSearchViewText(final String text){
        return new ViewAction(){
            @Override
            public Matcher<View> getConstraints() {
                //Ensure that only apply if it is a SearchView and if it is visible.
                return allOf(isDisplayed(), isAssignableFrom(SearchView.class));
            }

            @Override
            public String getDescription() {
                return "Change view text";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((SearchView) view).setQuery(text,false);
            }
        };
    }
}