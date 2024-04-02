package com.example.poke_recyclerview
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var pokemonRecyclerView: RecyclerView
    private var pokemonAdapter: PokemonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonRecyclerView = findViewById(R.id.pokemonRecyclerView)
        pokemonRecyclerView.layoutManager = LinearLayoutManager(this)

        fetchPokemonData()
    }

    private fun fetchPokemonData() {
        val client = OkHttpClient()
        val pokemonList = mutableListOf<Pokemon>()


        val pokemonIds = 1..30

        lifecycleScope.launch(Dispatchers.IO) {
            pokemonIds.forEach { id ->
                try {
                    val request = Request.Builder()
                        .url("https://pokeapi.co/api/v2/pokemon/$id")
                        .build()

                    val response = client.newCall(request).execute()
                    val responseBody = response.body?.string()

                    if (response.isSuccessful && responseBody != null) {
                        val jsonObject = JSONObject(responseBody)
                        val name = jsonObject.getString("name")
                        val imageUrl = jsonObject.getJSONObject("sprites").getString("front_default")
                        val type = jsonObject.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name")
                        val ability = jsonObject.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name")

                        val pokemon = Pokemon(name, type, ability, imageUrl)
                        pokemonList.add(pokemon)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            withContext(Dispatchers.Main) {
                pokemonAdapter = PokemonAdapter(pokemonList)
                pokemonRecyclerView.adapter = pokemonAdapter
            }
        }
    }

    data class Pokemon(
        val name: String,
        val type: String,
        val ability: String,
        val imageUrl: String
    )
}
