package com.example.exercise.ui.cardscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.ShoppingCart
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class CartViewModel : ViewModel() {

    @ExperimentalCoroutinesApi
    private val myShoppingCart = MutableLiveData<List<ShoppingCart>>()

    @ExperimentalCoroutinesApi
    fun getMyShoppingCart(): LiveData<List<ShoppingCart>> = myShoppingCart

    fun getMyClothesbyId() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = Firebase.firestore.collection("ShoppingCart")
                val result = response.get().await().toObjects(ShoppingCart::class.java)
                updateShoppingCart(result)
            }
        } catch (ie: Exception) {
            Log.d(ie.toString(),"Error")
        }
    }

    fun updateShoppingCart(clothes: List<ShoppingCart>) {
        viewModelScope.launch(Dispatchers.Main) {
            myShoppingCart.value = clothes
        }
    }

    fun deleteMyClothes() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val ids: String = Firebase.firestore.collection("ShoppingCart").document().id
                val test = Firebase.firestore.collection("ShoppingCart").document().delete().await()
                viewModelScope.launch(Dispatchers.Main) {
                    getMyClothesbyId()
                }
            }
        } catch (ie: Exception) {
        }
    }
}