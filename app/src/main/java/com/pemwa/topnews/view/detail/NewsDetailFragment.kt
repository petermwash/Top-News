package com.pemwa.topnews.view.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pemwa.topnews.R
import com.pemwa.topnews.databinding.NewsDetailFragmentBinding

/**
 * This [Fragment] shows the detailed information about a selected news item.
 * It sets this information in the [NewsDetailViewModel], which it gets as a Parcelable property
 * through Jetpack Navigation's SafeArgs.
 */
class NewsDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(activity).application
        val binding = NewsDetailFragmentBinding.inflate(inflater)

        // Getting the newsArticle passed from NewsOverviewFragment fro Bundle
        val newsArticle = NewsDetailFragmentArgs.fromBundle(arguments!!).selectedNewsItem

        // Creating an instance of the associated ViewModel
        val viewModelFactory = NewsDetailViewModelFactory(newsArticle, application)
        binding.viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(NewsDetailViewModel::class.java)

        // Allowing Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu, menu)
    }
}
