package org.berlin.logic

import org.berlin.model.Meal
import kotlin.math.abs
import kotlin.math.roundToInt

class GymHelperUseCase(private val mealsRepository: MealsRepository) {
    fun getMealsByCaloriesAndProtein(
        calories: Double,
        protein: Double,
        caloriesTolerance: Int = 30,
        proteinTolerance: Int = 10
    ): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                abs(meal.nutrition.calories.roundToInt() - calories.roundToInt()) <= caloriesTolerance &&
                abs(meal.nutrition.protein.roundToInt() - protein.roundToInt()) <= proteinTolerance
            }
    }
}



