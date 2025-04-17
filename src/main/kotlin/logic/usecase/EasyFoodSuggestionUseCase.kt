package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class EasyFoodSuggestionUseCase(
    private val mealsRepository: MealsRepository
) {
     fun getEasyFoodSuggestion(): Result<List<Meal>> {
         val filteredList = mealsRepository.getAllMeals()
             .filter { meal ->
                         meal.minutes <= 30 &&
                         meal.nIngredients <= 5 &&
                         meal.nSteps <= 6
             }
             .shuffled()
             .take(10)
         if (filteredList.isEmpty()){
             return Result.failure(Exception("No meals found"))
         }
         return Result.success(filteredList)
     }
}