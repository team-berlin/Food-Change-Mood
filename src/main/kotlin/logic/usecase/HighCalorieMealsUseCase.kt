package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal
import kotlin.random.Random

class HighCalorieMealsUseCase (private val mealsRepository: MealsRepository){
    private fun getHighCalorieMeals(calorieThreshold: Double = 700.0): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal -> meal.nutrition.calories > calorieThreshold }
            .sortedByDescending { it.nutrition.calories }
    }

    fun suggestHighCalorieMeal(calorieThreshold: Double = 700.0): Result<Meal> {
        val highCalorieMeals = getHighCalorieMeals(calorieThreshold = calorieThreshold)

        return if (highCalorieMeals.isEmpty()) {
            Result.failure(NoSuchElementException("No meals found with more than $calorieThreshold calories"))
        }else {
            val randomIndex = Random.nextInt(highCalorieMeals.size)
            Result.success(highCalorieMeals[randomIndex])
        }
    }
}