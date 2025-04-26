package org.berlin.logic.usecase.suggest

import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestKetoMealUseCase(
    private val mealsRepository: MealsRepository
) {

    fun suggestKetoMeal(): List<Meal> =
        mealsRepository.getAllMeals()
            .filter(::onlyKetoFriendly)
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?: throw NoSuchElementException("No keto friendly meals found")

    private fun onlyKetoFriendly(meal: Meal): Boolean {
        val carbValue = meal.nutrition.carbohydrates
        val proteinValue = meal.nutrition.proteinGrams
        val fatValue = meal.nutrition.totalFat

        val isCarbWithinThreshold = carbValue <= CARB_THRESHOLD
        val isFatProteinRatioValid = proteinValue == 0.0 ||
                (fatValue * FAT_CALORIE_FACTOR) / (proteinValue * PROTEIN_CALORIE_FACTOR) >= FAT_PROTEIN_RATIO_THRESHOLD

        return isCarbWithinThreshold && isFatProteinRatioValid
    }

    private companion object {
        const val CARB_THRESHOLD = 15.0
        const val FAT_PROTEIN_RATIO_THRESHOLD = 2.0
        const val FAT_CALORIE_FACTOR = 9.0
        const val PROTEIN_CALORIE_FACTOR = 4.0
    }
}
