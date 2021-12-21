package id.roogry.passtru.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.ViewInteraction;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import junit.framework.TestCase;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import id.roogry.passtru.R;

@RunWith(AndroidJUnit4ClassRunner.class)
public class ListSosmedActivityTest extends TestCase {
    private final String dummySosmed = "Linkedin";

    @Before
    public void setup(){
        ActivityScenario.launch(ListSosmedActivity.class);
    }

    @Test
    public void assertAddSosmed() {
        onView(withId(R.id.rvSosmed)).check(matches(isDisplayed()));
        onView(withId(R.id.fabAddSosmed)).perform(click());

        onView(withId(R.id.edtSosmed)).perform(typeText(dummySosmed), closeSoftKeyboard());
        onView(withId(R.id.btnSubmit)).perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.ivMore), withContentDescription("Threee Dots More Option"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.rvSosmed),
                                        0),
                                1),
                        isDisplayed()));
        appCompatImageView.perform(click());

        onView(withId(R.id.btnClose)).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}