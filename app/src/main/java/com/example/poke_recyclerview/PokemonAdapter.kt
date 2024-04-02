package com.example.poke_recyclerview
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PokemonAdapter(private val pokemonList: List<MainActivity.Pokemon>) :
    RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

    class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.pokemonImageView)
        val nameTextView: TextView = view.findViewById(R.id.pokemonNameTextView)
        val typeTextView: TextView = view.findViewById(R.id.pokemonTypeTextView)
        val abilityTextView: TextView = view.findViewById(R.id.pokemonAbilityTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        Picasso.get().load(pokemon.imageUrl).into(holder.imageView)
        holder.nameTextView.text = pokemon.name
        holder.typeTextView.text = pokemon.type
        holder.abilityTextView.text = pokemon.ability
    }

    override fun getItemCount() = pokemonList.size
}
