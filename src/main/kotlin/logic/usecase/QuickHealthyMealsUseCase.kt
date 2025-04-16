package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class QuickHealthyMealsUseCase(private val mealsRepository: MealsRepository) {
    fun getQuickHealthyMeals(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                meal.minutes <= 15 &&
                meal.tags.any { tag -> tag.contains("healthy", ignoreCase = true) }
            }.let { findLowFatAndCarbsMeals(it) }
    }

    private fun findLowFatAndCarbsMeals (meals: List<Meal>): List<Meal> {
        if (meals.isEmpty()) {
            return emptyList()
        }
        val avgTotalFat = meals.map { it.nutrition.totalFat }.average()
        val avgSaturatedFat = meals.map { it.nutrition.saturatedFat }.average()
        val avgCarbs = meals.map { it.nutrition.carbohydrates }.average()

        val sortedFilteredMeals = meals.filter { meal ->
            meal.nutrition.totalFat < avgTotalFat * 0.7 &&
            meal.nutrition.saturatedFat < avgSaturatedFat * 0.7 &&
            meal.nutrition.carbohydrates < avgCarbs * 0.7
        }.sortedBy { it.minutes }

        return sortedFilteredMeals
    }
}