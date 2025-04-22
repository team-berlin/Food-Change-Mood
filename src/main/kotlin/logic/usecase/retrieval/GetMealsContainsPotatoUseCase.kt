package org.berlin.logic.usecase.retrieval

import org.berlin.logic.common.extention.getRandomItems
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class GetMealsContainsPotatoUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getMealsContainsPotato(): List<String> =
        mealsRepository
            .getAllMeals()
            .filter(::containsPotatoIngredient)
            .takeIf { it.isNotEmpty() }
            ?.getRandomItems(10)
            ?.take(RANDOM_N)
            ?.map { it.name }
            ?: throw NoSuchElementException("No meals found that contain potato")

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