package org.berlin.logic.usecase.retrieval

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class QuickHealthyMealsUseCase(private val mealsRepository: MealsRepository) {
    companion object {
        const val MAX_MINUTES: Int = 15
        const val HEALTHY_TAG: String = "healthy"
        const val NUTRITION_THRESHOLD_MULTIPLIER: Double = 0.7
    }

    fun getQuickHealthyMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .asSequence()
            .filter { it.minutes <= MAX_MINUTES }
            .filter { it.tags.any { tag -> tag.contains(HEALTHY_TAG, ignoreCase = true) } }
            .toList()
            .let(::findLowFatAndCarbsMeals)
    }

    private fun findLowFatAndCarbsMeals(meals: List<Meal>): List<Meal> =
        meals.takeUnless { it.isEmpty() }
            ?.let { nonEmptyMeals ->
                val nutritionThresholds = calculateNutritionThresholds(nonEmptyMeals)

                nonEmptyMeals.filter { meal ->
                    with(meal.nutrition) {
                        totalFat < nutritionThresholds.first &&
                                saturatedFat < nutritionThresholds.second &&
                                carbohydrates < nutritionThresholds.third
                    }
                }.sortedBy { it.minutes }
            } ?: emptyList()

    private fun calculateNutritionThresholds(meals: List<Meal>): Triple<Double, Double, Double> =
        meals
            .map { it.nutrition }
            .let { nutritionList ->
                Triple(
                    nutritionList.map { it.totalFat }.average() * NUTRITION_THRESHOLD_MULTIPLIER,
                    nutritionList.map { it.saturatedFat }.average() * NUTRITION_THRESHOLD_MULTIPLIER,
                    nutritionList.map { it.carbohydrates }.average() * NUTRITION_THRESHOLD_MULTIPLIER
                )
            }
}
