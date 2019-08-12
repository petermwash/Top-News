package com.pemwa.topnews

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.pemwa.topnews.util.TestUtils.Companion.createArticle
import com.pemwa.topnews.view.NewsActivity
import com.pemwa.topnews.view.detail.NewsDetailFragment
import org.junit.Test


class TopNewsInstrumentedTest {

    private fun scenario() = ActivityScenario.launch(NewsActivity::class.java)

    @Test
    fun test_NewsOverviewAndDetailFragment() {
        scenario()

        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
        onView(withId(R.id.city_choice)).check(matches(isDisplayed()))
        onView(withId(R.id.news_item_list)).check(matches(isDisplayed()))
        onView(withText(R.string.tab_everything)).check(matches(isDisplayed()))
        onView(withText(R.string.tab_top_headlines)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.tab_everything)).check(matches(isDisplayed())).perform(click())

        scenario().close()
    }

    @Test
    fun test_NewsDetailFragment() {
        val article = createArticle()
        val fragmentArgs = Bundle()
        fragmentArgs.putParcelable("selectedNewsItem", article)
        val scenario = launchFragmentInContainer<NewsDetailFragment>(fragmentArgs = fragmentArgs)
        scenario.onFragment {
            it.setHasOptionsMenu(true)
        }
        onView(withText(R.string.app_name)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDetailTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDetailAuthor)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDetailContent)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDetailPublishedAt)).check(matches(isDisplayed()))
        onView(withId(R.id.btReadMore)).check(matches(isClickable())).perform(click())
    }
}