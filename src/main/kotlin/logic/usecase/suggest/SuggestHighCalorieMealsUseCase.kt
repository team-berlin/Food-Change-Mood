package org.berlin.logic.usecase.suggest

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal
import kotlin.random.Random

class SuggestHighCalorieMealsUseCase (private val mealsRepository: MealsRepository){
    companion object {
        const val CALORIE_THRESHOLD: Double = 700.0
    }

    private fun getHighCalorieMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .asSequence()
            .filter { it.nutrition.calories > CALORIE_THRESHOLD }
            .sortedByDescending { it.nutrition.calories }
            .toList()
    }

    fun suggestHighCalorieMeal(): Result<Meal> =
        getHighCalorieMeals().let { highCalorieMeals ->
            when {
                highCalorieMeals.isEmpty() -> Result.failure(
                    NoSuchElementException("No meals found with more than $CALORIE_THRESHOLD calories")
                )
                else -> highCalorieMeals
                    .randomOrNull()
                    ?.let { Result.success(it) }
                    ?: Result.failure(IllegalStateException("Unexpected error selecting random meal"))
            }
        }
}