package com.example.exercise.di

import com.example.exercise.ui.adapter.ShoppingCartAdapter
import com.example.exercise.ui.cardscreen.CartFragment
import com.example.exercise.ui.cardscreen.CartViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val CardModule = module {
    factory { ShoppingCartAdapter() }
    fragment { CartFragment() }
    viewModel { CartViewModel() }
}