package com.example.exercise.ui.detailscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.exercise.databinding.DetailScreenFragmentBinding
import com.example.model.ShoppingCart
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject
class DetailFragment : Fragment() {
    private lateinit var binding: DetailScreenFragmentBinding
    private val viewModel: DetailViewModel by inject()
    private lateinit var groupId: String
    private lateinit var image: String
    private var price: String = ""
    private var description: String = ""
    private var name: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DetailScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindToLiveData()
        bindMyMessage()
        groupId = arguments?.getString("id").toString()
        image = arguments?.getString("image").toString()
        price = arguments?.getString("price").toString()
        description = arguments?.getString("description").toString()
        name = arguments?.getString("name").toString()
        binding.productCardPrice.text = price
        binding.productCardDesc.text = description
        binding.productCardName.text = name


        Glide.with(requireView())
                .load(image)
                .into(binding.productCardImg)
        binding.button.setOnClickListener {
            viewModel.addItemToShoppingCart(ShoppingCart(name, image, price, description, null))
        }
    }
    fun bindToLiveData() {
        viewModel.getmyClothes().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
        })
    }

    fun bindMyMessage() {
        viewModel.getEvents().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                is DetailViewModel.Event.ShowMessage -> Snackbar.make(
                        requireView(),
                        it.msg,
                        Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }
}