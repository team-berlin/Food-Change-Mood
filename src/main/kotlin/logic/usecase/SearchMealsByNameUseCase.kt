package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.logic.search.SearchByName
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
