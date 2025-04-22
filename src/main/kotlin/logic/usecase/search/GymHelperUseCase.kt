package org.berlin.logic.usecase.search

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.GymHelperInput
import org.berlin.model.Meal
import kotlin.math.abs
import kotlin.math.roundToInt

class GymHelperUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getMealsByCaloriesAndProtein(input: GymHelperInput): List<Meal> =
        mealsRepository
            .getAllMeals()
            .filter { it.matchesCaloriesAndProtein(input) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException(
                "No meals found matching calories = ${input.calories} ± ${input.caloriesAndProteinTolerance.caloriesTolerance} " +
                        "and protein = ${input.protein} ± ${input.caloriesAndProteinTolerance.proteinTolerance}"
            )

    private fun Meal.matchesCaloriesAndProtein(input: GymHelperInput): Boolean {
        val calorieDifference = abs(this.nutrition.calories.roundToInt() - input.calories.roundToInt())
        val proteinDifference = abs(this.nutrition.protein.roundToInt() - input.protein.roundToInt())

        val withinCalorieTolerance = calorieDifference <= input.caloriesAndProteinTolerance.caloriesTolerance
        val withinProteinTolerance = proteinDifference <= input.caloriesAndProteinTolerance.proteinTolerance

        return withinCalorieTolerance && withinProteinTolerance
    }
}