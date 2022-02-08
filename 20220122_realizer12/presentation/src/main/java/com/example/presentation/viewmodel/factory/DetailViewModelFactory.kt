package com.example.presentation.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.RepoRepository
import com.example.presentation.viewmodel.DetailViewModel

class DetailViewModelFactory  (
    private val repoRepository: RepoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repoRepository = repoRepository) as T
            }
            else -> {
                throw Exception("cannot create viewModel")
            }
        }
    }

}