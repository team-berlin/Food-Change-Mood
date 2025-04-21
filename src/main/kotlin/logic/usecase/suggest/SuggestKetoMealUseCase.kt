package org.berlin.logic.usecase.suggest

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestKetoMealUseCase(
    private val mealsRepository: MealsRepository
) {

    fun suggestKetoMeal(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter (::onlyKetoFriendly)
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?: emptyList()
    }

    private fun onlyKetoFriendly(meal: Meal): Boolean {
        return  meal.nutrition.carbohydrates <= CARB_THRESHOLD &&
                (meal.nutrition.protein == 0.0 ||
                        (meal.nutrition.totalFat * 9) / (meal.nutrition.protein * 4) >= FAT_PROTEIN_RATIO_THRESHOLD)
    }

    private companion object {
        const val CARB_THRESHOLD = 15.0
        const val FAT_PROTEIN_RATIO_THRESHOLD = 2.0
    }
}
