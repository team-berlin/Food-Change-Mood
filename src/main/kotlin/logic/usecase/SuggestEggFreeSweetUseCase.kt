package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {
    private val suggestedSweets = mutableSetOf<String>()

    fun suggestEggFreeSweet() : Meal? {

        return mealsRepository.getAllMeals()
            .filter(::isEggFreeSweet)
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.first()
            ?.also { suggestedSweets.add(it.name) }
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