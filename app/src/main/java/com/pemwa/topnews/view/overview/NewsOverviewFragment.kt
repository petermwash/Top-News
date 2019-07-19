package com.pemwa.topnews.view.overview


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar
import com.pemwa.topnews.R
import com.pemwa.topnews.databinding.FragmentNewsOverviewBinding

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

    /**
     * Inflates the layout with Data Binding, sets its lifecycle owner to the [NewsOverviewFragment]
     * to enable Data Binding to observe LiveData, and sets up the RecyclerView with an adapter.
     */

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentNewsOverviewBinding.inflate(inflater)

        // Allowing DataBinding to observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving dataBinding access to the NewsOverviewFragment
        binding.viewModel = viewModel

        // Creating and setting the adapter of the RecyclerView
        val adapter = NewsOverviewAdapter()
        binding.newsItemList.adapter = adapter

        val tabLayout = binding.tabLayout
        // Observe the selected tab field
        viewModel.everythingTabSected.observe(this, Observer { isSelected ->
            isSelected?.let {
                viewModel.selectTheCorrectTab(tabLayout)
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


}
