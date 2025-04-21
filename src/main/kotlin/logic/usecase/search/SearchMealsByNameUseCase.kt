package org.berlin.logic.usecase.search

import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.SearchByName
import org.berlin.model.Meal

class SearchMealsByNameUseCase(
    private val mealsRepository: MealsRepository,
    private val searchByName: SearchByName
) {
    fun searchMealsByName(inputMealName: String): List<Meal> {
        val lowercaseInput=inputMealName.lowercase()
        return mealsRepository.getAllMeals().filter { meal ->
            searchByName.search(meal.name.lowercase(),lowercaseInput)
        }
    }

}
