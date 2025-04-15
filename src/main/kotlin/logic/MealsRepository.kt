package org.example.logic

import org.example.model.Meal

interface MealsRepository {

    fun getAllMeals(): List<Meal>
}