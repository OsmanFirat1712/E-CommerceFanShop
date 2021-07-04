package com.example.exercise.ui.homescreen

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.exercise.ui.adapter.FanShopAdapter
import com.example.exercise.databinding.HomeScreenBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var binding: HomeScreenBinding
    private val adapter: FanShopAdapter by inject()
    private val viewModel: HomeViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindToLiveData()
        viewModel.getClothes()

        binding.firstScreenRecylerView.apply {
            adapter = this@HomeFragment.adapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    }

    @ExperimentalCoroutinesApi
    fun bindToLiveData() {
        viewModel.getmyClothes().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })
    }
}