package org.berlin.logic.usecase.suggest

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestEggFreeSweetUseCase(
    private val mealsRepository: MealsRepository
) {
    private val suggestedSweets = mutableSetOf<String>()

    fun suggestEggFreeSweet(): Meal =
        mealsRepository
            .getAllMeals()
            .filter(::onlyEggFreeSweet)
            .takeIf { it.isNotEmpty() }
            ?.random()
            ?.also { suggestedSweets.add(it.name) }
            ?: throw NoSuchElementException("No egg‑free sweet found")

    private fun onlyEggFreeSweet(meal: Meal): Boolean {
        return meal.tags.any { tag ->
            tag.contains(SWEET_TAG, ignoreCase = true) && meal.ingredients.none { ingredient ->
                ingredient.equals(
                    EGG_INGREDIENT,
                    ignoreCase = true
                )
            } && !suggestedSweets.contains(meal.name)
        }
    }

    private companion object {
        const val SWEET_TAG = "sweet"
        const val EGG_INGREDIENT = "egg"
    }
}