package com.pemwa.topnews

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pemwa.topnews.view.overview.NewsOverviewViewModel
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsOverviewViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsOverviewViewModel

    @Mock
    val app = NewsApplication()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewsOverviewViewModel(app)
    }

}