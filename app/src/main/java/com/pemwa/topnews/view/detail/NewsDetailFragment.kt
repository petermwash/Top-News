package com.pemwa.topnews.view.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pemwa.topnews.R
import com.pemwa.topnews.databinding.NewsDetailFragmentBinding
import com.pemwa.topnews.view.showErrorSnackBar
import timber.log.Timber

/**
 * This [Fragment] shows the detailed information about a selected news item.
 * It sets this information in the [NewsDetailViewModel], which it gets as a Parcelable property
 * through Jetpack Navigation's SafeArgs.
 */
class NewsDetailFragment : Fragment() {

    private lateinit var binding: NewsDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        binding = NewsDetailFragmentBinding.inflate(inflater)

        // Getting the newsArticle passed from NewsOverviewFragment fro Bundle
        val newsArticle = NewsDetailFragmentArgs.fromBundle(arguments!!).selectedNewsItem

        // Creating an instance of the associated ViewModel
        val viewModelFactory = NewsDetailViewModelFactory(newsArticle, application)
        val viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsDetailViewModel::class.java)
        binding.viewModel = viewModel

        // Observing the filed that initiating the functionality to open a web page
        viewModel.openWebPage.observe(this, Observer {
            if (it) {
                newsArticle.url?.let { contentUrl ->
                    openOnWebPage(contentUrl) }
                viewModel.onOpenWebPageDone()
            }
        })

        // Allowing Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
    }

    /**
     * Launching a news article on a web page
     */
    private fun openOnWebPage(url: String) {
        try {
            val webPage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            startActivity(intent)

        } catch(e: ActivityNotFoundException) {
            showErrorSnackBar(binding.root,
                getString(R.string.web_page_error))
            Timber.e(e)
        }
    }
}
