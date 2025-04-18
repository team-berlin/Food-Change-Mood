package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.GymHelperInput
import org.berlin.model.Meal
import kotlin.math.abs
import kotlin.math.roundToInt

class GymHelperUseCase(private val mealsRepository: MealsRepository) {

    fun getMealsByCaloriesAndProtein(
        calorieAndProteinValues: GymHelperInput
    ): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                abs(meal.nutrition.calories.roundToInt() - calorieAndProteinValues.calories.roundToInt()) <= calorieAndProteinValues.caloriesAndProteinTolerance.caloriesTolerance &&
                        abs(meal.nutrition.protein.roundToInt() - calorieAndProteinValues.protein.roundToInt()) <= calorieAndProteinValues.caloriesAndProteinTolerance.proteinTolerance
            }
    }
}



