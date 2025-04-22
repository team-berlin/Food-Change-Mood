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
            .filter(::isSeafoodMeal)
            .takeIf { it.isNotEmpty() }
            ?.sortedByDescending { it.nutrition.protein }
            ?.map { SeafoodMeal(it.name, it.nutrition.protein) }
            ?: throw NoSuchElementException("No seafood meals found")


    private fun isSeafoodMeal(meal: Meal): Boolean =
        meal.tags.any { it.contains(TAG_NAME, ignoreCase = true) }

    companion object {
        const val TAG_NAME = "seafood"
    }
}