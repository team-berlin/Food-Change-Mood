package org.berlin.logic

import org.berlin.model.Meal
import kotlin.random.Random


class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {
    private val suggestedSweets = mutableSetOf<String>()

    fun suggestEggFreeSweet() : Meal? {
        val allMeals = mealsRepository.getAllMeals()
        val eggFreeSweets = allMeals.filter(::isEggFreeSweet)

        if (eggFreeSweets.isEmpty()) return null

        val randomSweet = eggFreeSweets.random(Random)
        suggestedSweets.add(randomSweet.name)
        return randomSweet
    }

    private fun isEggFreeSweet(meal: Meal): Boolean {
        return meal.tags.any { tag -> tag.contains("sweet", ignoreCase = true)
                && meal.ingredients.none { ingredient -> ingredient.equals("egg", ignoreCase = true) }
                && !suggestedSweets.contains(meal.name)
        }
    }
}