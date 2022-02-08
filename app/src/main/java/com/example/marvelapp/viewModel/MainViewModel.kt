package com.example.marvelapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.marvelapp.models.Results
import com.example.marvelapp.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class MainViewModel(val repository: CharacterRepository = CharacterRepository.getInstance() ) :
    ViewModel() {

    val searchQuery = MutableStateFlow("")

    val searchResult = searchQuery.flatMapLatest { query ->
        repository.searchCharacter(query).cachedIn(viewModelScope)
    }.asLiveData()

     var isNetworkAvailable : Boolean = false

    fun fetchCharacterData(): Flow<PagingData<Results>> {

        return repository.letCharacterDataFlow().cachedIn(viewModelScope)
    }

    fun addToFavourite(characterResult: Results) =
        viewModelScope.launch {
            repository.addCharacterToFavourite(characterResult)
        }

    fun removeFromFavourite(characterResult: Results) =
        viewModelScope.launch {
            repository.removeCharacterFromFavourite(characterResult)
        }

    fun getFavouriteCharacters() =
        repository.getFavouriteCharacters().asLiveData()

}
