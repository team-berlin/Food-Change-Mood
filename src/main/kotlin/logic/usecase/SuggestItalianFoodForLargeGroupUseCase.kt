package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class SuggestItalianFoodForLargeGroupUseCase(
    private val mealsRepository: MealsRepository
) {

    fun suggestItalianMealsForLargeGroup(): List<Meal>{
        return mealsRepository.getAllMeals()
            .filter(::isItalianFoodForLargeGroup)
    }

    private fun isItalianFoodForLargeGroup(meal: Meal): Boolean {
        return meal.tags.any { tag ->
            tag.equals(ITALIAN_FOOD_TAG, ignoreCase = true)
        }
                && meal.tags.any { tag ->
            tag.equals(LARGE_GROUP_TAG, ignoreCase = true)
        }
    }

    private companion object {
        const val ITALIAN_FOOD_TAG = "italian"
        const val LARGE_GROUP_TAG = "for-large-groups"
    }

}

