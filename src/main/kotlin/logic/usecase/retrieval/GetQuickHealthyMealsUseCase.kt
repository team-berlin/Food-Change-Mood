package org.berlin.logic.usecase.retrieval

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal
import org.berlin.model.NutritionThresholds

class GetQuickHealthyMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getQuickHealthyMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .asSequence()
            .filter { meal ->
                meal.minutes <= MAX_MINUTES &&
                        meal.tags.any { it.contains(HEALTHY_TAG, ignoreCase = true) }
            }.toList()
            .let(::filterLowFatAndCarbsMeals)
    }

    private fun filterLowFatAndCarbsMeals(meals: List<Meal>): List<Meal> =
        meals.takeUnless { it.isEmpty() }?.let { nonEmptyMeals ->
            val nutritionThresholds = calculateNutritionThresholds(nonEmptyMeals)

            nonEmptyMeals.filter { meal ->
                with(meal.nutrition) {
                    totalFat < nutritionThresholds.totalFat
                            && saturatedFat < nutritionThresholds.saturatedFat
                            && carbohydrates < nutritionThresholds.carbohydrates
                }
            }.sortedBy { it.minutes }
        } ?: emptyList()

    private fun calculateNutritionThresholds(meals: List<Meal>): NutritionThresholds =
        meals.map { it.nutrition }.let { nutritionList ->
            NutritionThresholds(
                totalFat = nutritionList.map { it.totalFat }
                    .average() * NUTRITION_THRESHOLD_MULTIPLIER,
                saturatedFat = nutritionList.map { it.saturatedFat }
                    .average() * NUTRITION_THRESHOLD_MULTIPLIER,
                carbohydrates = nutritionList.map { it.carbohydrates }
                    .average() * NUTRITION_THRESHOLD_MULTIPLIER
            )
        }

    private companion object {
        const val MAX_MINUTES: Int = 15
        const val HEALTHY_TAG: String = "healthy"
        const val NUTRITION_THRESHOLD_MULTIPLIER: Double = 0.7
    }
}