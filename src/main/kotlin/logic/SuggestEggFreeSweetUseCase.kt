package org.berlin.logic

import org.berlin.model.Meal
import kotlin.random.Random


class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {
    private val suggestedSweets = mutableSetOf<String>()

    fun suggestEggFreeSweet() : Meal? {
        val allMeals = mealsRepository.getAllMeals()
        val eggFreeSweets = allMeals.filter { meal ->
            meal.tags?.any { tag -> tag.contains("sweet", ignoreCase = true)
                    && meal.ingredients?.none { ingredient -> ingredient.equals("egg", ignoreCase = true) } == true
                    && !suggestedSweets.contains(meal.name)
            } == true
        }

        if (eggFreeSweets.isEmpty()) return null

        val randomSweet = eggFreeSweets.random(Random)
        return randomSweet
    }

}