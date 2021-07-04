package com.example.exercise.ui.detailscreen

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercise.R
import com.example.model.Clothes
import com.example.model.ShoppingCart
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DetailViewModel(private val context: Context) : ViewModel() {
    open class Event {
        data class ShowMessage(val msg: String) : Event()
        object Navigate : Event()
    }

    private val events = MutableLiveData<Event>()
    fun getEvents(): LiveData<Event> = events


    @ExperimentalCoroutinesApi
    private val myClothes = MutableLiveData<Clothes>()

    @ExperimentalCoroutinesApi
    fun getmyClothes(): LiveData<Clothes> = myClothes

    @ExperimentalCoroutinesApi
    private val myShoppingCart = MutableLiveData<List<ShoppingCart>>()

    @ExperimentalCoroutinesApi
    fun getMyShoppingCart(): LiveData<List<ShoppingCart>> = myShoppingCart


/*    fun getMyClothesbyId(id: String) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val test = Firebase.firestore.collection("Clothes").whereEqualTo("Hoodie", id)
                val result = test.get().await().toObjects(Clothes::class.java)
                updatePost(result)
            }
        } catch (ie: Exception) {
        }
    }*/


    fun addItemToShoppingCarts(name: ShoppingCart) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                Firebase.firestore.collection("ShoppingCart").add(name).addOnCompleteListener { tasks ->
                    tasks.getResult()?.id
                    viewModelScope.launch(Dispatchers.Main) {
                        if (tasks.isSuccessful) {
                            events.value =
                                    Event.ShowMessage(context.getString(R.string.eventAddedToShoppingCart))
                        } else {
                            events.value = Event.ShowMessage(context.getString(R.string.statusError))
                        }
                    }
                }
            }

        } catch (ie: Exception) {
        }
    }

    fun addItemToShoppingCart(name: ShoppingCart) {

        try {
            viewModelScope.launch(Dispatchers.IO) {
                Firebase.firestore.collection("ShoppingCart").add(name)
                viewModelScope.launch(Dispatchers.Main) {
                    events.value =
                            Event.ShowMessage(context.getString(R.string.eventAddedToShoppingCart))
                }
            }


        } catch (ie: Exception) {
            events.value = Event.ShowMessage(context.getString(R.string.statusError))
        }
    }

    fun updateShopping(shopping: List<ShoppingCart>) {
        viewModelScope.launch(Dispatchers.Main) {
            myShoppingCart.value = shopping
        }
    }
}