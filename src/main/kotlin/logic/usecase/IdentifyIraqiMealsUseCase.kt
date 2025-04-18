package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class IdentifyIraqiMealsUseCase(
    private val repository: MealsRepository
) {

    val allMeals by lazy {repository.getAllMeals()}
    fun identifyIraqiMeals(): List<Meal> {
        return allMeals.filter { isIraqiMeal(it) }
    }

    private fun isIraqiMeal(meal: Meal): Boolean {
       return meal.tags.any { tag -> tag.contains(IRAQI_TAG, ignoreCase = true) } ||
               meal.description?.contains(IRAQI_TAG, ignoreCase = true) == true
    }

    companion object {
        const val IRAQI_TAG = "iraqi"
    }
}