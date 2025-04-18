package org.berlin.logic

import kotlinx.datetime.LocalDate
import org.berlin.model.Meal

class SearchMealsByDateUseCase(
    private val mealsRepository: MealsRepository
) {
    fun searchMealsByDate(date: LocalDate): List<Meal> {
        try {
            return mealsRepository.getAllMeals().filter { meal ->
                meal.submissionDate == date
            }
        } catch (e: Exception) {
            println("Error while searching meals: ${e.message}")
            return emptyList()
        }
    }
}