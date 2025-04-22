package org.berlin.logic.usecase.retrieval

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal
import org.berlin.model.SeafoodMeal

class GetSeafoodMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getSeafoodMeals(): List<SeafoodMeal> =
        mealsRepository
            .getAllMeals()
            .filter(::onlySeafoodMeal)
            .takeIf { it.isNotEmpty() }
            ?.sortedByDescending { it.nutrition.proteinGrams }
            ?.map { SeafoodMeal(it.name, it.nutrition.proteinGrams) }
            ?: throw NoSuchElementException("No seafood meals found")

    private fun onlySeafoodMeal(meal: Meal): Boolean =
        meal.tags.any { it.contains(TAG_NAME, ignoreCase = true) }

    private companion object {
        const val TAG_NAME = "seafood"
    }
}