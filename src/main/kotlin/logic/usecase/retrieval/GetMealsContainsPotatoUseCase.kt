package org.berlin.logic.usecase.retrieval

import org.berlin.logic.common.extention.getRandomItems
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class GetMealsContainsPotatoUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getMealsContainsPotato(): List<String> {
        return mealsRepository.getAllMeals()
            .filter(::containsPotatoIngredient)
            .getRandomItems(10)
            .take(RANDOM_N)
            .map { it.name }
    }

    private fun containsPotatoIngredient(meal: Meal): Boolean {
        return meal.ingredients.any {
            it.lowercase().contains(POTATO_INGREDIENT)
        }
    }

    private companion object {
        const val RANDOM_N = 10
        const val POTATO_INGREDIENT = "potato"
    }
}