package ch.sheremet.katarina.backingapp;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.DisplayMetrics;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeTests {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;
    // https://stackoverflow.com/questions/26231752/android-espresso-tests-for-phone-and-tablet
    private boolean mScreenSw600dp;

    @Before
    public void screenSize() {
        mScreenSw600dp = isScreenSw600dp();
    }

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }

    @Test
    public void testRecipesLoaded() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void testNutellaPieStepsLoaded() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed())).perform(click());
        onView(withText(mActivityRule.getActivity().getString(R.string.recipe_igredients)))
                .check(matches(isDisplayed()));
        onView(withText("Finishing Steps")).check(matches(isDisplayed()));
    }

    @Test
    public void testNextPreviousExistInPortrait() {
        if (!mScreenSw600dp) {
            onView(withText("Nutella Pie")).check(matches(isDisplayed()));
            onView(withText("Nutella Pie")).perform(click());
            onView(withText("Recipe Introduction")).check(matches(isDisplayed())).perform(click());
            onView(withId(R.id.next_step_button)).check(matches(isDisplayed()));
            onView(withId(R.id.previous_step_button)).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testNextPreviousNavigation() {
        if (!mScreenSw600dp) {
            onView(withText("Nutella Pie")).check(matches(isDisplayed())).perform(click());
            onView(withText("Recipe Introduction")).check(matches(isDisplayed())).perform(click());
            onView(withId(R.id.next_step_button)).check(matches(isDisplayed())).perform(click());
            onView(withId(R.id.previous_step_button)).check(matches(isDisplayed())).perform(click());
            onView(withText("Recipe Introduction")).check(matches(isDisplayed()));
        }
    }

    @Test
    public void testIngredientsIsShown() {
        onView(withText("Nutella Pie")).check(matches(isDisplayed())).perform(click());
        onView(withText(mActivityRule.getActivity().getString(R.string.recipe_igredients)))
                .check(matches(isDisplayed())).perform(click());
        onView(withText("salt")).check(matches(isDisplayed()));
    }

    private boolean isScreenSw600dp() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        mActivityRule.getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        float widthDp = displayMetrics.widthPixels / displayMetrics.density;
        float heightDp = displayMetrics.heightPixels / displayMetrics.density;
        float screenSw = Math.min(widthDp, heightDp);
        return screenSw >= 600;
    }
}
