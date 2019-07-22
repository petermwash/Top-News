package com.pemwa.topnews

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.pemwa.topnews.view.NewsActivity
import org.junit.Test

class NewsDetailInstrumentedTest {

    private fun scenario() = ActivityScenario.launch(NewsActivity::class.java)

    @Test
    fun test_NewsDetail_launch() {
        scenario()

        onView(withId(R.id.news_item_list)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4), click())
    }
}