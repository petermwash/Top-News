package com.pemwa.topnews.view.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.pemwa.topnews.R
import com.pemwa.topnews.databinding.FragmentNewsOverviewBinding
import com.pemwa.topnews.domain.Article
import com.pemwa.topnews.view.isNetworkConnected

/**
 * This fragment shows the the status of the News Api web services transaction.
 */
class NewsOverviewFragment : Fragment() {

    /**
     * Lazily initialize our [NewsOverviewViewModel].
     */
    private val viewModel: NewsOverviewViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NewsOverviewViewModelFactory(activity.application))
            .get(NewsOverviewViewModel::class.java)
    }

    /**
     * RecyclerView Adapter for converting a list of articles to cards.
     */
    private var newsOverviewAdapter: NewsOverviewAdapter? = null

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.everythingTabSelected.observe(this, Observer { isSelected ->
            isSelected?.let {
                if (it) {
                    viewModel.everything.observe(
                        viewLifecycleOwner,
                        Observer<List<Article>> { everythingArticles ->
                            everythingArticles.apply {
                                viewModel.newsArticleList.value = everythingArticles
                            }
                        })
                }
            }
        })

        viewModel.topHeadlinesTabSelected.observe(this, Observer { isSelected ->
            isSelected?.let {
                if (it) {
                    viewModel.topHeadlines.observe(
                        viewLifecycleOwner,
                        Observer<List<Article>> { topHeadlinesArticles ->
                            topHeadlinesArticles.apply {
                                viewModel.newsArticleList.value = topHeadlinesArticles
                            }
                        })
                }
            }
        })
    }

    lateinit var binding: FragmentNewsOverviewBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the [NewsOverviewFragment]
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initBinding(inflater)
        initAdapter()

        /**
         * Setting the selected tab on the overview screen
         */
        viewModel.tabNavigated.observe(this, Observer {
            if (it) {
                viewModel.selectTheCorrectTab(binding.tabLayout)
                viewModel.tabNavigatedDone()
            }
        })

        /**
         * Observe the navigateToSelectedArticle LiveData and Navigate when it isn't null
         */
        viewModel.navigateToSelectedArticle.observe(this, Observer {
            launchDetails(it)
        })

        /**
         * Draw the city chips on the screen
         */
        drawChips(cityData)

        return binding.root
    }

    /**
     * Inflate the layout for this fragment and
     * Allowing DataBinding to observe LiveData with the lifecycle of this Fragment
     */
    private fun initBinding(inflater: LayoutInflater) {
        binding = FragmentNewsOverviewBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    /**
     * Setting the adapter in the RecyclerView (the newsItemList.adapter in the binding object)
     * to the [newsOverviewAdapter]
     */
    private fun initAdapter() {
        newsOverviewAdapter = NewsOverviewAdapter(OnItemClickListener {
            viewModel.displayArticleDetails(it)
        })
        binding.newsItemList.adapter = newsOverviewAdapter
    }

    private val cityData = listOf("New York", "Lagos","Nairobi", "Kampala", "Kigali")

    /**
     * A method to draw city chips on the screen
     */
    private fun drawChips(data: List<String>) {
        // Creating chipGroup and inflator variables.
        val chipGroup = binding.cityChoice
        val inflater = LayoutInflater.from(chipGroup.context)

        // Creating a Chip for each regionsList item.
        val children = data.map { cityName ->
            val chip = inflater.inflate(R.layout.city, chipGroup, false) as Chip
            chip.text = cityName
            chip.tag = cityName
            chip.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isNetworkConnected()) {
                    viewModel.onCityFilterChanged(buttonView.tag as String, isChecked)
                } else {
                    showNetworkError()
                }
            }
            chip
        }
        // Remove views that are already in chipGroup.
        chipGroup.removeAllViews()

        // Finally, iterate through the list of children to add each chip to chipGroup
        for (chip in children) {
            chipGroup.addView(chip)
        }
    }

    /**
     * A method to navigate to the Detail view screen
     */
    private fun launchDetails(selectedArticle: Article?) {
        if (null != selectedArticle) {
            // Must find the NavController from the Fragment
            this.findNavController()
                .navigate(
                    NewsOverviewFragmentDirections.actionNewsOverviewFragmentToNewsDetailFragment(
                        selectedArticle
                    )
                )
            // Tell the ViewModel we've made the navigate call to prevent multiple navigation
            viewModel.displayArticleDetailsComplete()
        }
    }

    /**
     * A method to show an internet error on a a snackBar
     */
    private fun showNetworkError() {
        Snackbar.make(binding.root, R.string.check_network_connectivity, Snackbar.LENGTH_LONG)
            .show()
    }


}
