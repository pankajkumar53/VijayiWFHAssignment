package com.pandroid.vijayiwfhassignment.di

import com.pandroid.vijayiwfhassignment.data.api.ApiClient
import com.pandroid.vijayiwfhassignment.data.repo.MediaRepository
import com.pandroid.vijayiwfhassignment.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.create() }
    single { MediaRepository(get()) }
    viewModel { HomeViewModel(get()) }
}