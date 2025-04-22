package org.berlin.logic.usecase.suggest

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestItalianFoodForLargeGroupUseCase(
    private val mealsRepository: MealsRepository
) {

    fun suggestItalianMealsForLargeGroup(): List<Meal> =
        mealsRepository
            .getAllMeals()
            .filter(::onlyItalianFoodForLargeGroup)
            .takeIf { it.isNotEmpty() }
            ?: throw NoSuchElementException(
                "No Italian meals found suitable for a large group"
            )

    private fun onlyItalianFoodForLargeGroup(meal: Meal): Boolean {
        return meal.tags.any { tag ->
            tag.equals(ITALIAN_FOOD_TAG, ignoreCase = true)
        } && meal.tags.any { tag ->
            tag.equals(LARGE_GROUP_TAG, ignoreCase = true)
        }
    }

    private companion object {
        const val ITALIAN_FOOD_TAG = "italian"
        const val LARGE_GROUP_TAG = "for-large-groups"
    }

}

