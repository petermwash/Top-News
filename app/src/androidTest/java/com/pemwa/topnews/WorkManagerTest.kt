package com.pemwa.topnews

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import com.pemwa.topnews.work.RefreshDataWorker
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class WorkManagerTest {
    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testRefreshDataWorker() {
        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<RefreshDataWorker>(context).build()

        // Run the worker synchronously with retry
        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.retry()))
        }

        // Run the worker synchronously with success
        runBlocking {
            val result = worker.doWork()
            sleep(5000)
            assertThat(result, `is`(ListenableWorker.Result.retry()))
        }
    }
}