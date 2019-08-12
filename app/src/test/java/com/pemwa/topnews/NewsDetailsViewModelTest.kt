package com.pemwa.topnews

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pemwa.topnews.util.TestUtils.Companion.createArticle
import com.pemwa.topnews.view.detail.NewsDetailViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: NewsDetailViewModel

    @Mock
    val app = Application()

    private val article = createArticle()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewsDetailViewModel(article, app)
    }

    @Test
    fun test_liveDataFields() {
        assertNotNull(viewModel.selectedArticle)

        viewModel.openWebPage.value?.let { assertFalse(it) }

        viewModel.onOpenWebPage()
        viewModel.openWebPage.value?.let { assertTrue(it) }

        viewModel.onOpenWebPageDone()
        viewModel.openWebPage.value?.let { assertFalse(it) }

    }
}