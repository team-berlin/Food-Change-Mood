package org.berlin.logic

import org.berlin.model.Meal

interface MealsRepository {

    fun getAllMeals(): List<Meal>
}