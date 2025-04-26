package org.berlin.logic.usecase.search

import logic.usecase.helper.SelectionOfSearchAlgorithms
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SearchMealsByNameUseCase(
    private val mealsRepository: MealsRepository,
    private val searchByName: SelectionOfSearchAlgorithms
) {

    fun searchMealsByName(inputMealName: String): List<Meal> {
        val query = inputMealName.lowercase()

        return mealsRepository
            .getAllMeals()
            .filter { onlyMatchesName(it, query) }
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException("No meals found for \"$inputMealName\"")
    }

    private fun onlyMatchesName(meal: Meal, query: String): Boolean =
        searchByName.search(
            textToSearchIn  = meal.name.lowercase(),
            wordToSearchBy = query
        )
}