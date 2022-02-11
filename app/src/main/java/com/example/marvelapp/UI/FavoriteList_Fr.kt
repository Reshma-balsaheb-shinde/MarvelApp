package com.example.marvelapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.Application.MarvelApplication
import com.example.marvelapp.R
import com.example.marvelapp.adapter.CharacterAdapter
import com.example.marvelapp.adapter.FavouriteCharacterAdapter
import com.example.marvelapp.databinding.FragmentFavoriteListBinding
import com.example.marvelapp.databinding.FragmentFavoriteListBindingImpl
import com.example.marvelapp.models.Results
import com.example.marvelapp.viewModel.FavViewModelFactory
import com.example.marvelapp.viewModel.FavouriteCharacterViewModel
import com.example.marvelapp.viewModel.MainViewModel
import com.example.marvelapp.viewModel.MainViewModelFactory


@ExperimentalPagingApi
class FavoriteList_Fr : Fragment() , CharacterAdapter.CharacterClickListener {
    lateinit var binding: FragmentFavoriteListBinding
    private lateinit var favouriteCharacterViewModel: FavouriteCharacterViewModel
    private lateinit var viewModelFactory: FavViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = (activity?.application as MarvelApplication).characterRepository

        viewModelFactory = FavViewModelFactory(repository)
        favouriteCharacterViewModel = ViewModelProvider(this, viewModelFactory)
            .get(FavouriteCharacterViewModel::class.java)


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
