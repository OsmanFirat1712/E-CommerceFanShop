package com.example.exercise.ui.adapter

import com.example.exercise.databinding.CardViewFragmentBinding
import com.example.exercise.databinding.CardViewItemBinding
import com.example.model.ShoppingCart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.exercise.R
import com.example.exercise.databinding.FanShopItemBinding
import com.example.model.Clothes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class ShoppingCartAdapter : ListAdapter<ShoppingCart, ShoppingCartAdapter.MainViewHolder>(
        DiffCallback()
) {
    lateinit var mItemCLicked: ItemCLickedListener


    class DiffCallback : DiffUtil.ItemCallback<ShoppingCart>() {
        override fun areItemsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingCart, newItem: ShoppingCart): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
                CardViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
        val curentItem = getItem(position)
        holder.itemView.setOnLongClickListener {
            holder.apply {
                mItemCLicked.let {
                    mItemCLicked.onItemClicked(getItem(position))
                }
            }
            true
        }
    }

    fun setUpListener(itemCLicked: ItemCLickedListener) {
        mItemCLicked = itemCLicked
    }

    class MainViewHolder(private val binding: CardViewItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(postData: ShoppingCart) {
            binding.titles.text = postData.name
            binding.price.text = postData.price

            Glide.with(itemView)
                    .load(postData.image)
                    .into(binding.previewIcon)
        }
    }

    interface ItemCLickedListener {
        fun onItemClicked(shoppingCart: ShoppingCart)
    }

}