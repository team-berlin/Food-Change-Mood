package org.berlin.logic

import org.berlin.model.Meal
import kotlin.random.Random


class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {
    private val suggestedSweets = mutableSetOf<String>()

    fun suggestEggFreeSweet() : Meal? {
        val allMeals = mealsRepository.getAllMeals()
        val eggFreeSweets = allMeals.filter(::filterEggFreeSweets)

        if (eggFreeSweets.isEmpty()) return null

        val randomSweet = eggFreeSweets.random(Random)
        suggestedSweets.contains(randomSweet.name)
        return randomSweet
    }

    private fun filterEggFreeSweets(meal: Meal): Boolean {
        return meal.tags.any { tag -> tag.contains("sweet", ignoreCase = true)
                && meal.ingredients.none { ingredient -> ingredient.equals("egg", ignoreCase = true) }
                && !suggestedSweets.contains(meal.name)
        }
    }
}