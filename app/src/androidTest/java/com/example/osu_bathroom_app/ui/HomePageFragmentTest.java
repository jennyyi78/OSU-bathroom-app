package com.example.osu_bathroom_app.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.osu_bathroom_app.R;
import com.example.osu_bathroom_app.main.HomePageFragment;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HomePageFragmentTest extends TestCase
{

    private FragmentScenario<HomePageFragment> scenario;
    @Before
    public void setup()
    {
        scenario = FragmentScenario.launchInContainer(HomePageFragment.class);
        scenario.moveToState(Lifecycle.State.STARTED);
    }

    @Test
    public void VerifyButtonText()
    {
        onView(withId(R.id.map_btn)).check(matches(withText("Map")));
        onView(withId(R.id.bathroom_list_btn)).check(matches(withText("Bathroom List")));
        onView(withId(R.id.user_profile_btn)).check(matches(withText("User Profile")));
        onView(withId(R.id.my_reviews_btn)).check(matches(withText("My Reviews")));

    }
}