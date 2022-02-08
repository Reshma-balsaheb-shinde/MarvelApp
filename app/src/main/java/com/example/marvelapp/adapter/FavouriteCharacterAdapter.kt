package com.example.marvelapp.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.marvelapp.R
import com.example.marvelapp.databinding.ItemCharacterBinding
import com.example.marvelapp.models.Results


@ExperimentalPagingApi
class FavouriteCharacterAdapter(private val listener: CharacterAdapter.CharacterClickListener):
    ListAdapter<Results, FavouriteCharacterAdapter.FavouriteCharacterViewHolder>(CHARACTER_COMPARATOR) {

    object CHARACTER_COMPARATOR : DiffUtil.ItemCallback<Results>() {
        override fun areItemsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem.characterId == newItem.characterId
        }

        override fun areContentsTheSame(oldItem: Results, newItem: Results): Boolean {
            return oldItem == newItem
        }
    }

    inner class FavouriteCharacterViewHolder(
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
            itemFav.setImageResource(R.drawable.ic_fav)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteCharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val favouriteCharacterViewHolder = FavouriteCharacterViewHolder(binding)

        binding.itemFav.setOnClickListener {
            listener.removeFromFavourite(getItem(favouriteCharacterViewHolder.absoluteAdapterPosition))
        }
        return favouriteCharacterViewHolder
    }

    override fun onBindViewHolder(holder: FavouriteCharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bindCharacter(character)
    }
}