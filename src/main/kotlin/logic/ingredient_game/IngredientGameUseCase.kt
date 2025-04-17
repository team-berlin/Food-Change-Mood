package org.berlin.logic.ingredient_game

import org.berlin.model.GameState
import org.berlin.model.MealForIngredientGame

class IngredientGameUseCase(
    private val ingredientGame: IngredientGame
) {

    private var gameCurrentMeal: MealForIngredientGame = ingredientGame.getMeal()
        ?: throw InvalidInputForIngredientGameException("Meal Not Found Meal")
    private var gameIngredients = gameCurrentMeal.threeIngredientOnlyOneCorrect

    fun getCurrentMealIngredients(): List<String> {
        return gameCurrentMeal.threeIngredientOnlyOneCorrect

    }

    fun getCurrentMealName() = gameCurrentMeal.mealName
    fun getTurnResult(): String {
        return when (ingredientGame.getState()) {
            GameState.WON -> {
                "You Won your Score , ${ingredientGame.getScore()}"
            }

            GameState.LOST -> {
                "You Lost your Score , ${ingredientGame.getScore()}"
            }

            else -> "Game Is Going"
        }
    }

    fun submitInput(answer: Int) {
        if (answer in 1..3)
            ingredientGame.updateGameState(gameCurrentMeal, gameIngredients[answer - 1])
        else
            throw InvalidInputForIngredientGameException("Invalid Input Ingredient Only 3")

        if (ingredientGame.isRunning()) {
            gameCurrentMeal = ingredientGame.getMeal()
                ?: throw InvalidInputForIngredientGameException("Meal Not Found Meal")

            gameIngredients = gameCurrentMeal.threeIngredientOnlyOneCorrect
        }
    }

    fun run() {
        ingredientGame.reset()
        gameCurrentMeal = ingredientGame.getMeal()
            ?: throw InvalidInputForIngredientGameException("Meal Not Found Meal")
        gameIngredients = gameCurrentMeal.threeIngredientOnlyOneCorrect
    }

    fun isRunning(): Boolean {
        return ingredientGame.getState() == GameState.RUNNING
    }
}