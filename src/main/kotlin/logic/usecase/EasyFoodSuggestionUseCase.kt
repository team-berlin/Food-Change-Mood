package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class EasyFoodSuggestionUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getEasyFoodSuggestion(): List<Meal> {
        return mealsRepository.getAllMeals()
            .filter(::onlyEasyFood)
            .takeIf { it.isNotEmpty() }
            ?.shuffled()
            ?.take(RANDOM_N)
            ?: emptyList()
    }

    private fun onlyEasyFood(meal: Meal): Boolean {
        return meal.minutes <= MAX_PREP_TIME_MINUTES &&
                meal.nIngredients <= INGREDIENTS_COUNT &&
                meal.nSteps <= STEPS_COUNT
    }

    private companion object{
         const val MAX_PREP_TIME_MINUTES = 30
         const val INGREDIENTS_COUNT = 5
         const val STEPS_COUNT = 6
         const val RANDOM_N = 10
    }
}

