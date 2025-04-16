package org.berlin.logic

import org.berlin.model.Meal

class GetMealsContainsPotatoUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getMealsContainsPotato (): List<String>{
        return mealsRepository.getAllMeals()
            .filter(::onlyMealsWithPotato)
            .shuffled()
            .take(10)
            .map { it.name }
    }

    private fun onlyMealsWithPotato(input: Meal): Boolean{
        return input.ingredients.any{ it.lowercase().removeSuffix("es") == "potato" }
    }
}