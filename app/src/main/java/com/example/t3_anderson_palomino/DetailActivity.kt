package com.example.t3_anderson_palomino

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.t3_anderson_palomino.api.RetrofitClient
import com.example.t3_anderson_palomino.databinding.ActivityDetailBinding
import com.example.t3_anderson_palomino.model.MealDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idMeal = intent.getStringExtra("ID_MEAL")
        if (idMeal != null) {
            obtenerDetalle(idMeal)
        } else {
            Toast.makeText(this, "ID inválido", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun obtenerDetalle(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.getMealById(id)
                runOnUiThread {
                    val meal = response.body()?.meals?.firstOrNull()
                    if (response.isSuccessful && meal != null) {
                        mostrarDatos(meal)
                    } else {
                        Toast.makeText(
                            this@DetailActivity,
                            "No se encontró el detalle del platillo",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(this@DetailActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun mostrarDatos(meal: MealDetail) {
        binding.txtNombre.text = meal.strMeal
        binding.txtCategoria.text = "Categoría: ${meal.strCategory}"
        binding.txtArea.text = "Origen: ${meal.strArea}"
        binding.txtIdMeal.text = "ID: ${meal.idMeal}"

        binding.txtYoutube.text=meal.strYoutube
        //binding.txtYoutube.text = "Ver en YouTube 🎬"
        binding.txtYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(meal.strYoutube))
            startActivity(intent)
        }

        Glide.with(this)
            .load(meal.strMealThumb)
            .into(binding.imgDetalle)
    }
}
