package org.berlin.logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.MealForIngredientGame

class IngredientGameUseCase(
    repository: MealsRepository,
    ingredientGameMealsMapper: IngredientGameMealsMapper
) {

    private val meals=repository.getAllMeals()
    private val mealsForGame=ingredientGameMealsMapper.map(meals)
    fun getFifteenMeals(): List<MealForIngredientGame> {
        if (mealsForGame.isEmpty()||mealsForGame.size< MAX_QUESTIONS)throw Exception("need more meals to run a game")
        return mealsForGame
            .shuffled()
            .take(MAX_QUESTIONS)
    }
    private companion object Constants {
        const val MAX_QUESTIONS = 15

    }
}