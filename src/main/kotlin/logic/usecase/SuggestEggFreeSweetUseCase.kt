package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal
import kotlin.random.Random

class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {
    private val suggestedSweets = mutableSetOf<String>()

    private val allMeals  by lazy { mealsRepository.getAllMeals() }
    fun suggestEggFreeSweet() : Meal? {

        val eggFreeSweets = allMeals.filter(::isEggFreeSweet)

        if (eggFreeSweets.isEmpty()) return null

        val randomSweet = eggFreeSweets.random(Random.Default)
        suggestedSweets.add(randomSweet.name)
        return randomSweet
    }

    private fun isEggFreeSweet(meal: Meal): Boolean {
        return meal.tags.any { tag -> tag.contains(SWEET_TAG, ignoreCase = true)
                && meal.ingredients.none { ingredient -> ingredient.equals(EGG_INGREDIENT, ignoreCase = true) }
                && !suggestedSweets.contains(meal.name)
        }
    }

    companion object {
        private const val SWEET_TAG = "sweet"
        private const val EGG_INGREDIENT = "egg"
    }
}