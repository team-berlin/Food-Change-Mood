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
            tag.equals("italian", ignoreCase = true)
        }
                && meal.tags.any { tag ->
            tag.equals("for-large-groups", ignoreCase = true)
        }
    }

}

