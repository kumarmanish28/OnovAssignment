package com.mktech.onovassignment.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mktech.onovassignment.R
import com.mktech.onovassignment.data.model.Character
import com.mktech.onovassignment.databinding.ItemCharacterBinding

class CharacterAdapter(
    private val onItemClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val characterList = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    fun submitList(data: List<Character>) {
        characterList.clear()
        characterList.addAll(data)
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {

            binding.characterName.text = character.name
            binding.characterSpecies.text = "${character.species} • ${character.status}"

            Glide.with(itemView.context)
                .load(character.image)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(binding.characterImage)

            itemView.setOnClickListener {
                onItemClick(character)
            }
        }
    }
}
