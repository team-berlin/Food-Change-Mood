package org.berlin.logic.usecase.suggest

import org.berlin.logic.common.extention.getRandomItems
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal

class SuggestEasyFoodUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getEasyFoodSuggestion(): List<Meal> =
        mealsRepository
            .getAllMeals()
            .filter(::onlyEasyFood)
            .takeIf { it.isNotEmpty() }
            ?.getRandomItems(RANDOM_N)
            ?: throw NoSuchElementException("No easy food meals found")

    private fun onlyEasyFood(meal: Meal): Boolean {
        return meal.minutes <= MAX_PREP_TIME_MINUTES &&
                meal.numberOfIngredients <= INGREDIENTS_COUNT &&
                meal.numberOfSteps <= STEPS_COUNT
    }

    private companion object {
        const val MAX_PREP_TIME_MINUTES = 30
        const val INGREDIENTS_COUNT = 5
        const val STEPS_COUNT = 6
        const val RANDOM_N = 10
    }

}


