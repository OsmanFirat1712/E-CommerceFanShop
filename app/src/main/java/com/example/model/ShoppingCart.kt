package com.example.model

import com.google.firebase.firestore.DocumentId

data class ShoppingCart(
        val name: String ="",
        val image: String = "",
        val price: String = "",
        val description: String = "",
        @DocumentId
        val id: String? = ""
)