package com.example.osu_bathroom_app.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.osu_bathroom_app.R
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class HomeNavigationTest {

    @Test
    fun setup()
    {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.username)).perform(typeText("jennyyi78@gmail.com") )
        onView(withId(R.id.password)).perform(typeText("password"))
        onView(withId(R.id.login_fragment_parent)).check(matches(isDisplayed())) //verify
        onView(withId(R.id.loginButton)).perform(click())
        //verify the home page is opened
        //onView(withId(R.id.map_btn)).perform(click())
    }

}