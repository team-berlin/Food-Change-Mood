package org.berlin.logic.ingredient_game

import org.berlin.model.MealForIngredientGame

class IngredientGame(
    private val mealsFormatedForGame: List<MealForIngredientGame>
) {

    fun getFiveteenMeals(): List<MealForIngredientGame> {
        return mealsFormatedForGame.shuffled().take(MAX_QUESTIONS)
    }

    private companion object Constants {
        const val MAX_QUESTIONS = 15

    }
}