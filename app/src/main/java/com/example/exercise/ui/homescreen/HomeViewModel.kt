package com.example.exercise.ui.homescreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Clothes
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class HomeViewModel() : ViewModel() {

    @ExperimentalCoroutinesApi
    private val myClothes = MutableLiveData<List<Clothes>>()

    @ExperimentalCoroutinesApi
    fun getmyClothes(): LiveData<List<Clothes>> = myClothes

    fun getClothes() {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = Firebase.firestore.collection("Clothes")
                val result = response.get().await().toObjects(Clothes::class.java)
                updatePost(result)
            }
        } catch (ie: Exception) {
            Log.d(ie.toString(),"Fehler")
        }
    }
    fun updatePost(clothes: List<Clothes>) {
        viewModelScope.launch(Dispatchers.Main) {
            myClothes.value = clothes
        }
    }
}