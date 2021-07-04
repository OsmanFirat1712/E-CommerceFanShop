package com.example.exercise.di

import com.example.exercise.ui.detailscreen.DetailFragment
import com.example.exercise.ui.detailscreen.DetailViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val DetailScreenModule = module {

    fragment { DetailFragment() }
    viewModel { DetailViewModel(get()) }
}