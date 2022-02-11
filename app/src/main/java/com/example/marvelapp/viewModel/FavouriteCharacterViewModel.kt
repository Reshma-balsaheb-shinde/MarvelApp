package com.example.marvelapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import com.example.marvelapp.models.Results
import com.example.marvelapp.repository.CharacterRepository
import kotlinx.coroutines.launch


@ExperimentalPagingApi
class FavouriteCharacterViewModel(val characterRepository: CharacterRepository ) :
    ViewModel() {

    fun addToFavourite(characterResult: Results) =
        viewModelScope.launch {
            characterRepository.addCharacterToFavourite(characterResult)
        }

    fun removeFromFavourite(characterResult: Results) =
        viewModelScope.launch {
            characterRepository.removeCharacterFromFavourite(characterResult)
        }

    fun getFavouriteCharacters() =
        characterRepository.getFavouriteCharacters().asLiveData()

}