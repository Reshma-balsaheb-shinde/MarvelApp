package com.example.marvelapp.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marvelapp.Application.GlideApp
import com.example.marvelapp.adapter.CharacterAdapter
import com.example.marvelapp.adapter.CharactersLoadStateAdapter
import com.example.marvelapp.common.NetworkUtils
import com.example.marvelapp.databinding.FragmentCharacterListBinding
import com.example.marvelapp.databinding.FragmentCharacterListBindingImpl
import com.example.marvelapp.models.Results
import com.example.marvelapp.viewModel.MainViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class CharacterListFr : Fragment(), CharacterAdapter.CharacterClickListener {
    lateinit var binding: FragmentCharacterListBinding
   private lateinit var mainViewModel: MainViewModel
    private lateinit var CharactersAdapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterListBindingImpl.inflate(inflater)
        mainViewModel = defaultViewModelProviderFactory.create(MainViewModel::class.java)

        binding.mainActivityErrorView.errorLayoutRetryButton.setOnClickListener {
            setupView()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindings()
        setupView()
        setupSerachview()

    }

    private fun setupSerachview() {

        val searchView = binding.searchview

        searchView.queryHint = "Search Character"
        searchView.setIconifiedByDefault(false)
        searchView.onActionViewExpanded()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    mainViewModel.searchQuery.value = newText
                }
                return true
            }
        })

        mainViewModel.searchResult.observe(viewLifecycleOwner) {
            CharactersAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initBindings() {


        CharactersAdapter = CharacterAdapter(this)
        binding.rvCharacterlist.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CharactersAdapter.withLoadStateHeaderAndFooter(
                header = CharactersLoadStateAdapter { CharactersAdapter.retry() },
                footer = CharactersLoadStateAdapter { CharactersAdapter.retry() }
            )
            setHasFixedSize(true)
        }

        mainViewModel.getFavouriteCharacters().observe(viewLifecycleOwner) {
            CharactersAdapter.updateFavourites(it)
        }
    }

    private fun setupView() {
        if(NetworkUtils.isInternetAvailable(requireActivity()))
        {
            lifecycleScope.launch {

                binding.mainActivityErrorView.root.visibility = View.GONE
                binding.mainActivityProgressview.root.visibility = View.GONE
                mainViewModel.fetchCharacterData().distinctUntilChanged().collectLatest {
                    CharactersAdapter.submitData(it)
                }
            }
       }
        else{
            binding.mainActivityErrorView.root.visibility = View.VISIBLE
            binding.mainActivityProgressview.root.visibility = View.GONE
        }

    }

    override fun addToFavourite(character: Results) {
        mainViewModel.addToFavourite(character)
    }

    override fun removeFromFavourite(character: Results) {
        mainViewModel.removeFromFavourite(character)
    }
}