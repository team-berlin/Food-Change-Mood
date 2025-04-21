package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class GetMealsContainsPotatoUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getMealsContainsPotato (): List<String>{
        return mealsRepository.getAllMeals()
            .filter(::containsPotatoIngredient)
            .shuffled()
            .take(RANDOM_N)
            .map { it.name }
    }

    private fun containsPotatoIngredient(meal: Meal): Boolean{
        return meal.ingredients.any{ it.lowercase().contains(POTATO_INGREDIENT) }
    }

    private companion object{
        const val RANDOM_N = 10
        const val POTATO_INGREDIENT = "potato"
    }
}