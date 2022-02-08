package com.example.marvelapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.R
import com.example.marvelapp.adapter.CharacterAdapter
import com.example.marvelapp.adapter.CharactersLoadStateAdapter
import com.example.marvelapp.adapter.FavouriteCharacterAdapter
import com.example.marvelapp.databinding.FragmentCharacterListBinding
import com.example.marvelapp.databinding.FragmentCharacterListBindingImpl
import com.example.marvelapp.databinding.FragmentFavoriteListBinding
import com.example.marvelapp.databinding.FragmentFavoriteListBindingImpl
import com.example.marvelapp.models.Results
import com.example.marvelapp.viewModel.FavouriteCharacterViewModel
import com.example.marvelapp.viewModel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class FavoriteList_Fr : Fragment() , CharacterAdapter.CharacterClickListener {
    lateinit var binding: FragmentFavoriteListBinding
    private lateinit var favouriteCharacterViewModel: FavouriteCharacterViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favouriteCharacterViewModel = defaultViewModelProviderFactory.create(FavouriteCharacterViewModel::class.java)

        initBindings()

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBindingImpl.inflate(inflater)
        return binding.root
    }

    private fun initBindings() {

        val favouriteCharacterAdapter = FavouriteCharacterAdapter(this)

        binding.recyclerview.apply {
            adapter = favouriteCharacterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        favouriteCharacterViewModel.getFavouriteCharacters().observe(viewLifecycleOwner) {
            favouriteCharacterAdapter.submitList(it)
        }

    }



    override fun addToFavourite(character: Results) {
        favouriteCharacterViewModel.addToFavourite(character)
    }

    override fun removeFromFavourite(character: Results) {
        favouriteCharacterViewModel.removeFromFavourite(character)
    }







}
