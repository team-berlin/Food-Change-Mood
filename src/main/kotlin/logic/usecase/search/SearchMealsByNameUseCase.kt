package org.berlin.logic.usecase.search

import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.search.CombineSearchAlgorithms
import org.berlin.model.Meal

class SearchMealsByNameUseCase(
    private val mealsRepository: MealsRepository,
    private val searchByName: CombineSearchAlgorithms
) {

    fun searchMealsByName(inputMealName: String): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter { meal ->
                searchByName.search(
                    textToSearchIn = meal.name.lowercase(),
                    wordToSearchBy = inputMealName.lowercase()
                )
            }
    }

}