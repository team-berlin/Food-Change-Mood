package org.berlin.logic.repository

import org.berlin.model.Meal

interface MealsRepository {
    fun getAllMeals(): List<Meal>
}