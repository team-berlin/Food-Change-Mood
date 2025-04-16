package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class SuggestKetoMealUseCase(
    private val mealsRepository: MealsRepository
) {

    fun suggestMeal(
        excludedMealIds: MutableSet<Int>
    ): Meal {
        return mealsRepository.getAllMeals()
            .filter (::isKetoFriendly)
            .filter { meal -> meal.id !in excludedMealIds }
            .shuffled()
            .first()
    }

    private fun isKetoFriendly(meal: Meal): Boolean {
        return meal.nutrition.carbohydrates <= CARB_THRESHOLD &&
                (meal.nutrition.protein == 0.0 ||
                        (meal.nutrition.totalFat * 9) / (meal.nutrition.protein * 4) >= FAT_PROTEIN_RATIO_THRESHOLD)
    }

    companion object {
        private val CARB_THRESHOLD = 15.0
        private val FAT_PROTEIN_RATIO_THRESHOLD = 2.0
    }
}
