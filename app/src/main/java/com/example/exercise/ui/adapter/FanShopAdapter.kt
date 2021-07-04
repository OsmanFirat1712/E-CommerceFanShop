package com.example.exercise.ui.adapter

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


class FanShopAdapter : ListAdapter<Clothes, FanShopAdapter.MainViewHolder>(
        DiffCallback()
) {
    class DiffCallback : DiffUtil.ItemCallback<Clothes>() {
        override fun areItemsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Clothes, newItem: Clothes): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
                FanShopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MainViewHolder(private val binding: FanShopItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(postData: Clothes) {
            binding.titles.text = postData.name
            binding.price.text = postData.price
            binding.size.text = postData.size

            Glide.with(itemView)
                    .load(postData.image)
                    .into(binding.previewIcon)

            val bundle = bundleOf(
                    "name" to postData.name,
                    "id" to postData.id,
                    "description" to postData.description,
                    "price" to postData.price,
                    "image" to postData.image


            )
            binding.titles.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_home_view_to_details, bundle)
            }
        }

    }
}