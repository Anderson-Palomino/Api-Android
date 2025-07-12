package com.example.t3_anderson_palomino

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.t3_anderson_palomino.api.RetrofitClient
import com.example.t3_anderson_palomino.databinding.ActivityMainBinding
import com.example.t3_anderson_palomino.model.Meal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MealAdapter

    private val categorias = listOf(
        "Beef", "Chicken", "Dessert", "Lamb", "Miscellaneous", "Pasta",
        "Pork", "Seafood", "Side", "Starter", "Vegan", "Vegetarian", "Breakfast", "Goat"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar adaptador para las categorías
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, categorias)
        binding.autoCategoria.setAdapter(spinnerAdapter)

        // Evitar que se abra el teclado
        binding.autoCategoria.inputType = InputType.TYPE_NULL

        // Mostrar la lista al hacer clic
        binding.autoCategoria.setOnClickListener {
            binding.autoCategoria.showDropDown()
        }

        // Configurar RecyclerView
        adapter = MealAdapter(emptyList()) { meal ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("ID_MEAL", meal.idMeal)
            startActivity(intent)
        }
        binding.recyclerComidas.layoutManager = LinearLayoutManager(this)
        binding.recyclerComidas.adapter = adapter

        // Listener de selección de categoría
        binding.autoCategoria.setOnItemClickListener { _, _, position, _ ->
            val categoria = categorias[position]
            Toast.makeText(this, "Seleccionaste: $categoria", Toast.LENGTH_SHORT).show()
            obtenerComidasPorCategoria(categoria)
        }
    }

    private fun obtenerComidasPorCategoria(categoria: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.getMealsByCategory(categoria)
                runOnUiThread {
                    if (response.isSuccessful && response.body() != null) {
                        val meals: List<Meal> = response.body()!!.meals
                        adapter.updateList(meals)
                    } else {
                        Toast.makeText(this@MainActivity, "Error al cargar datos", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
