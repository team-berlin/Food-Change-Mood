package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal

class EasyFoodSuggestionRepository(
    private val mealsRepository: MealsRepository
) {
     fun getEasyFoodSuggestion(): List<Meal> {
         val filteredList = mealsRepository.getAllMeals().filter { meal ->
             meal.minutes <= 30 && meal.nIngredients <= 5 && meal.nSteps <= 6
         }
             .shuffled()
             .take(10)
         return filteredList
     }
}