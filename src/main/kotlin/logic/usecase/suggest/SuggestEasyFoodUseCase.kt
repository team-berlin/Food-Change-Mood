package org.berlin.logic.usecase.suggest

import org.berlin.logic.common.extention.getRandomItems
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestEasyFoodUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getEasyFoodSuggestion(): List<Meal> {
        val filterList = mealsRepository.getAllMeals()
            .filter(::onlyEasyFood)
        return if (filterList.size <= RANDOM_N) {
            filterList
        } else {
            filterList.getRandomItems(RANDOM_N)
        }
    }

    private fun onlyEasyFood(meal: Meal): Boolean {
        return meal.minutes <= MAX_PREP_TIME_MINUTES &&
                meal.nIngredients <= INGREDIENTS_COUNT &&
                meal.nSteps <= STEPS_COUNT
    }

    private companion object {
        const val MAX_PREP_TIME_MINUTES = 30
        const val INGREDIENTS_COUNT = 5
        const val STEPS_COUNT = 6
        const val RANDOM_N = 10
    }

}


