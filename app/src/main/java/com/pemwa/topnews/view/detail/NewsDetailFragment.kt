package com.pemwa.topnews.view.detail

import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.pemwa.topnews.R
import com.pemwa.topnews.databinding.NewsDetailFragmentBinding
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.view.showErrorSnackBar
import timber.log.Timber

/**
 * This [Fragment] shows the detailed information about a selected news item.
 * It sets this information in the [NewsDetailViewModel], which it gets as a Parcelable property
 * through Jetpack Navigation's SafeArgs.
 */
class NewsDetailFragment : Fragment() {

    private lateinit var binding: NewsDetailFragmentBinding
    private lateinit var newsArticle: Article

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        binding = NewsDetailFragmentBinding.inflate(inflater)

        // Getting the newsArticle passed from NewsOverviewFragment fro Bundle
        newsArticle = NewsDetailFragmentArgs.fromBundle(arguments!!).selectedNewsItem

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.shareArticle -> shareArticle()
            R.id.copyArticleLink -> copyArticleLink()
        }
        return true
    }

    /**
     * Method for sharing an article via user's contacts or social media
     */
    private fun shareArticle() {
        val message = "Hey, Checkout this awesome article ${newsArticle.url}"
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(Intent.createChooser(shareIntent, "Share article via..."))
    }

    /**
     * Method for copying the article url to clipboard
     */
    private fun copyArticleLink() {
        val clipboard = context?.getSystemService(ClipboardManager::class.java)
        val clipData = ClipData.newRawUri("Article url", Uri.parse(newsArticle.url))
        clipboard?.primaryClip = clipData
        Toast.makeText(context, "Article Copied", Toast.LENGTH_SHORT).show()
    }

    /**
     * Launching a news article on a web page
     */
    private fun openOnWebPage(url: String) {
        val webPage: Uri = Uri.parse(url)
        val webIntent = Intent(Intent.ACTION_VIEW, webPage)
        startActivity(webIntent)
    }
}
