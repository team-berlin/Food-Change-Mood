package org.berlin.logic.usecase.search

import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.search.CombineSearchAlgorithms
import org.berlin.model.Meal

class SearchMealsByNameUseCase(
    private val mealsRepository: MealsRepository,
    private val searchByName: CombineSearchAlgorithms
) {

    fun searchMealsByName(inputMealName: String): List<Meal> {
        val query = inputMealName.lowercase()

        return mealsRepository
            .getAllMeals()
            .filter { matchesName(it, query) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("No meals found for \"$inputMealName\"")
    }

    private fun matchesName(meal: Meal, query: String): Boolean =
        searchByName.search(
            textToSearchIn  = meal.name.lowercase(),
            wordToSearchBy = query
        )
}