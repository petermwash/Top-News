package com.pemwa.topnews.view.overview


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
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

        return binding.root
    }


}
