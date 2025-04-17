package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class EasyFoodSuggestionUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getEasyFoodSuggestion(): Result<List<Meal>> {
        val filteredList = mealsRepository.getAllMeals()
            .filter { meal ->
                meal.minutes <= MAX_PREP_TIME_MINUTES &&
                        meal.nIngredients <= INGREDIENTS_COUNT &&
                        meal.nSteps <= STEPS_COUNT
            }
            .shuffled()
            .take(RANDOM_N)
        if (filteredList.isEmpty()){
            return Result.failure(Exception("No meals found"))
        }
        return Result.success(filteredList)
    }
    companion object{
         private const val MAX_PREP_TIME_MINUTES = 30
         private const val INGREDIENTS_COUNT = 5
         private const val STEPS_COUNT = 6
         private const val RANDOM_N = 10
    }
}

