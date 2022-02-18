package com.example.marvelapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.example.marvelapp.repository.CharacterRepository

@ExperimentalPagingApi
internal class FavViewModelFactory (private val repository: CharacterRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteCharacterViewModel::class.java)) {
            return FavouriteCharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}