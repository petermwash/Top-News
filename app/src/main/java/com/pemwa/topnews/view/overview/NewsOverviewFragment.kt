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
import com.pemwa.topnews.view.isNetworkConnected

/**
 * This fragment shows the the status of the News Api web services transaction.
 */
class NewsOverviewFragment : Fragment() {

    /**
     * Lazily initialize our [NewsOverviewViewModel].
     */
    private val viewModel: NewsOverviewViewModel by lazy {
        ViewModelProviders.of(this).get(NewsOverviewViewModel::class.java)
    }

    private lateinit var binding: FragmentNewsOverviewBinding

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the [NewsOverviewFragment]
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewsOverviewBinding.inflate(inflater)

        // Allowing DataBinding to observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving dataBinding access to the NewsOverviewFragment
        binding.viewModel = viewModel
        viewModel.selectTheCorrectTab(binding.tabLayout)

        // Observe the selected tab field
        viewModel.everythingTabSelected.observe(this, Observer { isSelected ->
            if (isNetworkConnected()) {
                isSelected?.let {
                    if (it) {
                        viewModel.getAllNewsItems()
                    } else {
                        viewModel.getTopHeadlines()
                    }
                }
            } else {
                showNetworkError()
            }
        })

        // Setting the adapter in the RecyclerView (the newsItemList.adapter in the binding object)
        // to the NewsOverviewAdapter
        binding.newsItemList.adapter = NewsOverviewAdapter(OnItemClickListener {
            viewModel.displayArticleDetails(it)
        })

        // Observe network availability
        viewModel.networkConnected.observe(this, Observer { isConnected ->
            if (!isConnected) {
                showNetworkError()
            }
        })

        // Observe the navigateToSelectedArticle LiveData and Navigate when it isn't null
        // After navigating, call displayArticleDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedArticle.observe(this, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController()
                    .navigate(NewsOverviewFragmentDirections.actionNewsOverviewFragmentToNewsDetailFragment(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayArticleDetailsComplete()
            }
        })

        viewModel.cityList.observe(viewLifecycleOwner, object : Observer<List<String>> {
            override fun onChanged(data: List<String>?) {
                data ?: return

                val chipGroup = binding.cityChoice
                val inflater = LayoutInflater.from(chipGroup.context)

                val children = data.map { cityName ->
                    val chip = inflater.inflate(R.layout.city, chipGroup, false) as Chip
                    chip.text = cityName
                    chip.tag = cityName
                    chip.setOnCheckedChangeListener { buttonView, isChecked ->
                        Snackbar.make(binding.root, "Chip has been selected", Snackbar.LENGTH_LONG).show()
                    }
                    chip
                }
                chipGroup.removeAllViews()

                for (chip in children) {
                    chipGroup.addView(chip)
                }
            }
        })

        return binding.root
    }

    fun showNetworkError() {
        Snackbar.make(binding.root, R.string.check_network_connectivity, Snackbar.LENGTH_LONG).show()
    }


}
