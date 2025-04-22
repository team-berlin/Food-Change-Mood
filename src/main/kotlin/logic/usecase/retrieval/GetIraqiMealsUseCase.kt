package org.berlin.logic.usecase.retrieval

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class GetIraqiMealsUseCase(
    private val repository: MealsRepository
) {

    fun getIraqiMeals(): List<Meal> =
        repository
            .getAllMeals()
            .filter(::onlyIraqiMeal)
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("No Iraqi meals found")

    private fun onlyIraqiMeal(meal: Meal): Boolean {
        return meal.tags.any { tag ->
            tag.contains(
                IRAQI_TAG, ignoreCase = true
            )
        } || meal.description?.contains(
            IRAQI_TAG, ignoreCase = true
        ) == true
    }

    private companion object {
        const val IRAQI_TAG = "iraqi"
    }
}