package com.example.exercise

import android.app.Application
import com.example.exercise.di.CardModule
import com.example.exercise.di.DetailScreenModule
import com.example.exercise.di.HomeFeedScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.startKoin

class MyApp: Application() {
    @KoinExperimentalAPI
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            fragmentFactory()
            modules(HomeFeedScreenModule, DetailScreenModule, CardModule)
        }
    }


}