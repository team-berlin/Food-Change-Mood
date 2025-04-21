package org.berlin.presentation

import logic.usecase.game.IngredientGameUseCase
import org.berlin.logic.InvalidInputForIngredientGameException
import org.berlin.model.GameState
import org.berlin.model.MealForIngredientGame

class IngredientGameInteractor(
    private val ingredientGame: IngredientGameUseCase
) {
    private var meals: List<MealForIngredientGame> = emptyList()
    private var currentIndex = 0
    private var score = 0
    private var state = GameState.RUNNING

    fun run() {
        meals = ingredientGame.getFifteenMeals()
        currentIndex = 0
        score = 0
        state = GameState.RUNNING
    }

    fun getCurrentIngredients() = meals[currentIndex].threeIngredientOnlyOneCorrect

    fun submitUserAnswer(answer: Int) {
        validateUserAnswer(answer)
        val ingredientAnswer = fetchIngredientAnswer(answer)
        updateState(ingredientAnswer, meals[currentIndex])
    }

    private fun validateUserAnswer(answer: Int) {
        if (answer !in MIN_ANSWER..MAX_ANSWER) {
            throw InvalidInputForIngredientGameException("Invalid Input: Only $MIN_ANSWER to $MAX_ANSWER are allowed.")
        }
    }

    private fun fetchIngredientAnswer(answer: Int): String {
        return getCurrentIngredients()[answer - ANSWER_OFFSET]
    }

    private fun updateState(ingredientAnswer: String,
                                       meal: MealForIngredientGame) {
        if (ingredientAnswer == meal.correctIngredient) {
            score += QUESTION_SCORE
            currentIndex++
            if (score == MAX_SCORE) state = GameState.WON
        } else {
            state = GameState.LOST
        }
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

    fun getCurrentMealName() = meals[currentIndex].mealName

    private companion object {
        const val MIN_ANSWER = 1
        const val MAX_ANSWER = 3
        const val ANSWER_OFFSET = 1
        const val MAX_QUESTIONS = 15
        const val QUESTION_SCORE = 1000
        const val MAX_SCORE = MAX_QUESTIONS * QUESTION_SCORE
    }

}