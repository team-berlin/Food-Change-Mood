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

    fun suggestHighCalorieMeal(): Result<Meal> =
        getHighCalorieMeals().randomOrNull()?.let { Result.success(it) } ?: Result.failure(
            NoSuchElementException(
                "No meals found with more than $CALORIE_THRESHOLD calories"
            )
        )

    companion object {
        const val CALORIE_THRESHOLD: Double = 700.0
    }

}