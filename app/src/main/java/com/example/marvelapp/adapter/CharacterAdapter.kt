package com.example.marvelapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelapp.Application.GlideRequests
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.models.Results


@ExperimentalPagingApi
class CharacterAdapter(private val listener: CharacterClickListener):
    PagingDataAdapter<Results, CharacterAdapter.CharactersViewHolder>(CharactersComparator) {

    var favourites: List<Results> = ArrayList()

    object CharactersComparator : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.characterId == newItem.characterId
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharactersViewHolder {

        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val allCharacterViewHolder = CharactersViewHolder(binding)
        Log.i("Rohit Fav", favourites.size.toString())
        binding.itemFav.setOnClickListener {
            val position = allCharacterViewHolder.absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val character = getItem(position)
                if (character != null) {
                    val favCharacter = favourites.find { it.id == character.id }
                    if (favCharacter != null) {
                        listener.removeFromFavourite(character)
                    } else {
                        listener.addToFavourite(character)
                    }
                }
            }
        }
        return allCharacterViewHolder
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindCharacter(it) }
    }

    inner class CharactersViewHolder(
        private val binding: ItemCharacterBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindCharacter(item: Results) = with(binding) {

            Glide.with(characterImage).load(finalPath(item.thumbnail!!.path!!, item.thumbnail!!.extension!!))
                .placeholder(R.drawable.ic_noun_iron_man)
                .error(R.drawable.ic_noun_iron_man)
                .into(characterImage)
            itemCharacterName.text = item.name

            if (item.description == "") {
                itemCharacterDescription.text = "No Description"
            } else {
                itemCharacterDescription.text = item.description
            }


            val favCharacter = favourites.find { it.id == item.id }
            if (favCharacter != null) {
                itemFav.setImageResource(R.drawable.ic_fav)
            } else {
                itemFav.setImageResource(R.drawable.ic_fav_not)
            }

        }

        fun finalPath(path: String, extension: String): String {
            var finalpath: String
            if (!path.isNullOrEmpty() && !extension.isNullOrEmpty()) {
                finalpath = "${path.makeSecurePath()}.$extension"
            } else {
                finalpath = ""
            }
            return finalpath
        }

        fun String.makeSecurePath() = if (this.startsWith("http://")) {
            this.replace("http://", "https://")
        } else {
            this
        }



    }

    fun updateFavourites(favouritesUpdated: List<Results>) {
        favourites = favouritesUpdated
        notifyDataSetChanged()
    }


    interface CharacterClickListener {
       // fun onClick(character: Results)
        fun addToFavourite(character: Results)
        fun removeFromFavourite(character: Results)
    }
}


