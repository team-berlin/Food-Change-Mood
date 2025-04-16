package org.berlin.logic

import kotlinx.datetime.LocalDate
import org.berlin.model.Meal

interface SearchMealsByDateUseCase {
    fun searchMealsByDate(date: LocalDate): List<Meal>
}