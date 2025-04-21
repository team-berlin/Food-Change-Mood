package org.berlin.logic.usecase.retrieval

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class IdentifyIraqiMealsUseCase(
    private val repository: MealsRepository
) {

    fun identifyIraqiMeals(): List<Meal> {
        return repository.getAllMeals()
            .filter { isIraqiMeal(it) }
    }

    private fun isIraqiMeal(meal: Meal): Boolean {
       return meal.tags.any { tag -> tag.contains(IRAQI_TAG, ignoreCase = true) } ||
               meal.description?.contains(IRAQI_TAG, ignoreCase = true) == true
    }

    companion object {
        const val IRAQI_TAG = "iraqi"
    }
}