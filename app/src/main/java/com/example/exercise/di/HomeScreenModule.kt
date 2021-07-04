package com.example.exercise.di

import com.example.exercise.ui.adapter.FanShopAdapter
import com.example.exercise.ui.homescreen.HomeFragment
import com.example.exercise.ui.homescreen.HomeViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val HomeFeedScreenModule = module {

    factory { FanShopAdapter() }
    fragment { HomeFragment() }
    viewModel { HomeViewModel() }
}