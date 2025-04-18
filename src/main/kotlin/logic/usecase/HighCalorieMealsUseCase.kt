package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal
import kotlin.random.Random

class HighCalorieMealsUseCase (private val mealsRepository: MealsRepository){
    companion object {
        const val CALORIE_THRESHOLD: Double = 700.0
    }

    private fun getHighCalorieMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal -> meal.nutrition.calories > CALORIE_THRESHOLD }
            .sortedByDescending { it.nutrition.calories }
    }

    fun suggestHighCalorieMeal(): Result<Meal> {
        val highCalorieMeals = getHighCalorieMeals()

        return if (highCalorieMeals.isEmpty()) {
            Result.failure(NoSuchElementException("No meals found with more than $CALORIE_THRESHOLD calories"))
        }else {
            val randomIndex = Random.nextInt(highCalorieMeals.size)
            Result.success(highCalorieMeals[randomIndex])
        }
    }
}