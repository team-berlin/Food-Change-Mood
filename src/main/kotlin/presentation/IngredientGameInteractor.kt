package org.berlin.presentation

import org.berlin.logic.ingredient_game.IngredientGame
import org.berlin.logic.ingredient_game.InvalidInputForIngredientGameException
import org.berlin.model.GameState
import org.berlin.model.MealForIngredientGame

class IngredientGameInteractor(
    private val ingredientGame: IngredientGame
) {
    private var meals: List<MealForIngredientGame> = emptyList()
    private var currentIndex = 0
    private var score = 0
    private var state = GameState.RUNNING

    fun run() {
        meals = ingredientGame.getFiveteenMeals()
        currentIndex = 0
        score = 0
        state = GameState.RUNNING
    }

    private fun getCurrentMeal() = meals[currentIndex].takeIf { state == GameState.RUNNING }
    fun getCurrentIngredients() = getCurrentMeal()?.threeIngredientOnlyOneCorrect ?: emptyList()
    fun submitAnswer(answer: Int) {
        val meal = getCurrentMeal() ?: return
        if (answer !in 1..3) throw InvalidInputForIngredientGameException("Invalid Input Ingredient Only 3")

        val ingredientAnswer = getCurrentIngredients()[answer - 1]
        if (ingredientAnswer == meal.correctIngredient) {
            score += QUESTION_SCORE
            currentIndex++
            if (score == MAX_SCORE) state = GameState.WON
        } else state = GameState.LOST

    }

    fun getTurnResult(): String {
        return when (state) {
            GameState.WON -> {
                "You Won your Score , $score"
            }

            GameState.LOST -> {
                "You Lost your Score , $score"
            }

            else -> "Game Is Going"
        }
    }

    fun isRunning(): Boolean {
        return state == GameState.RUNNING
    }

    fun getCurrentMealName() = getCurrentMeal()?.mealName ?: throw InvalidInputForIngredientGameException()

    private companion object {
        private const val MAX_QUESTIONS = 15
        private const val QUESTION_SCORE = 1000
        private const val MAX_SCORE = MAX_QUESTIONS * QUESTION_SCORE
    }

}