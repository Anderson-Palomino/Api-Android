package com.example.t3_anderson_palomino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.t3_anderson_palomino.model.Meal

class MealAdapter(
    private var mealList: List<Meal>,
    private val onItemClick: (Meal) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        val meal = mealList[position]
        holder.bind(meal)
        holder.itemView.setOnClickListener {
            onItemClick(meal)
        }
    }

    override fun getItemCount(): Int = mealList.size

    fun updateList(newList: List<Meal>) {
        mealList = newList
        notifyDataSetChanged()
    }

    class MealViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgMeal: ImageView = itemView.findViewById(R.id.imgMeal)
        private val txtMeal: TextView = itemView.findViewById(R.id.txtMeal)

        fun bind(meal: Meal) {
            txtMeal.text = meal.strMeal
            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .into(imgMeal)
        }
    }
}
