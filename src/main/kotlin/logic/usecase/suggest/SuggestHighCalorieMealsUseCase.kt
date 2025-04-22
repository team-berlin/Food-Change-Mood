package org.berlin.logic.usecase.suggest

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestHighCalorieMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    private fun getHighCalorieMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter(::onlyHighCalorieMeal)
            .sortedByDescending { it.nutrition.calories }
    }

    private fun onlyHighCalorieMeal(meal: Meal): Boolean =
        meal.nutrition.calories > CALORIE_THRESHOLD

    fun suggestHighCalorieMeal(): Meal =
        getHighCalorieMeals()
            .randomOrNull()
            ?: throw NoSuchElementException(
                "No meals found with more than $CALORIE_THRESHOLD calories"
            )

    private companion object {
        const val CALORIE_THRESHOLD: Double = 700.0
    }
}