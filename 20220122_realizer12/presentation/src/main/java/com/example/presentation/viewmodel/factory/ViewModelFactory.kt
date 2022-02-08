package com.example.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.presentation.repository.UserRepository
import com.example.presentation.viewmodel.MainViewModel
import com.example.presentation.viewmodel.SplashViewModel

class ViewModelFactory(
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userRepository = userRepository) as T
            }
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(userRepository = userRepository) as T
            }
            else -> {
                throw Exception("cannot create viewModel")
            }
        }
    }

}