package org.berlin.logic.ingredient_game

import org.berlin.logic.MealsRepository
import org.berlin.model.MealForIngredientGame

class IngredientGame(
    private val repository: MealsRepository,
    private val ingredientGameMealsMapper: IngredientGameMealsMapper
) {

    fun getFifteenMeals(): List<MealForIngredientGame> {
        return ingredientGameMealsMapper.map(
            repository.getAllMeals()
        ).shuffled().take(MAX_QUESTIONS)
    }

    private companion object Constants {
        const val MAX_QUESTIONS = 15

    }
}