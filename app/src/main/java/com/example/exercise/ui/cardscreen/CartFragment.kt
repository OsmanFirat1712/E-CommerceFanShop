package com.example.exercise.ui.cardscreen

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exercise.R
import com.example.exercise.databinding.CardViewFragmentBinding
import com.example.exercise.ui.adapter.ShoppingCartAdapter
import com.example.model.ShoppingCart
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.inject

class CartFragment:Fragment(){
    private lateinit var binding: CardViewFragmentBinding
    private val viewModel: CartViewModel by inject()
    private val adapter: ShoppingCartAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = CardViewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.shoppingcartrecyclerview.apply {
            adapter = this@CartFragment.adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        bindToLiveDataShoppingCart()
        viewModel.getMyClothesbyId()
    }

    fun bindToLiveDataShoppingCart() {
        viewModel.getMyShoppingCart().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)

            adapter.setUpListener(object : ShoppingCartAdapter.ItemCLickedListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onItemClicked(shoppingCart: ShoppingCart) {
                    val dialogBuilder = AlertDialog.Builder(requireContext())
                            .setCancelable(true)
                            .setPositiveButton(
                                    "abbrechen",
                                    DialogInterface.OnClickListener { dialog, id ->
                                    })
                            .setNegativeButton(
                                    getString(R.string.alertDelete),
                                    DialogInterface.OnClickListener { dialog, id ->
                                        val test = Firebase.firestore.collection("ShoppingCart").document()

                                        viewModel.deleteMyClothes()
                                        dialog.cancel()
                                    })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Möchtest du ${shoppingCart.name} wirklich aus dem Warenkorb löschen?")
                    alert.show()
                }
            })
        })
    }



}