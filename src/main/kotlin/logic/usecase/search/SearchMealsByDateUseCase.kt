package org.berlin.logic.usecase.search

import kotlinx.datetime.LocalDate
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SearchMealsByDateUseCase(
    private val mealsRepository: MealsRepository
) {
    fun searchMealsByDate(date: LocalDate): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { onlySelectedDate(it, date) }
            .ifEmpty { emptyList() }
    }

    private fun onlySelectedDate(meal: Meal, date: LocalDate): Boolean {
        return meal.submissionDate == date
    }
}