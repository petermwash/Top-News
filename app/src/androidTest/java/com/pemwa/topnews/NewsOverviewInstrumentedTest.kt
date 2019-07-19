package com.pemwa.topnews

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.pemwa.topnews.view.NewsActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test


class NewsOverviewInstrumentedTest {

    private fun scenario() = ActivityScenario.launch(NewsActivity::class.java)

    @Test
    fun test_Toolbar_IsDisplayed() {
        scenario()

        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
    }

    @Test
    fun test_OverviewFragment_ShouldDisplay_NewsItems_OnRecyclerView() {
        scenario()

        onView(withId(R.id.news_item_list)).check(matches(isDisplayed()))
    }

    @Test
    fun test_OverviewFragment_EverythingAndTopHeadlines_Tabs() {
        scenario()

        onView(withText(R.string.tab_everything)).check(matches(isDisplayed()))
        onView(withText(R.string.tab_top_headlines)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.tab_everything)).check(matches(isDisplayed())).perform(click())
    }

    @Test
    fun test_ListOfCityChips_IsDisplayed() {
        scenario()

        onView(withId(R.id.city_choice)).check(matches(isDisplayed()))
        onView(allOf(withId(R.id.city_choice))).check(matches(isDisplayed())).perform(click())
    }
}