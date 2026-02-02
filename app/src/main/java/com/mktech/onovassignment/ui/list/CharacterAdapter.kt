package com.mktech.onovassignment.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mktech.onovassignment.R
import com.mktech.onovassignment.data.model.Character

class CharacterAdapter(
    private val onItemClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

     private val characterList = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
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

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView: ImageView = itemView.findViewById(R.id.characterImage)
        private val nameTv: TextView = itemView.findViewById(R.id.characterName)
        private val speciesTv: TextView = itemView.findViewById(R.id.characterSpecies)

        fun bind(character: Character) {

            nameTv.text = character.name
            speciesTv.text = "${character.species} • ${character.status}"

            Glide.with(itemView.context)
                .load(character.image)
                .centerCrop()
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .into(imageView)

            itemView.setOnClickListener {
                onItemClick(character)
            }
        }
    }
}
