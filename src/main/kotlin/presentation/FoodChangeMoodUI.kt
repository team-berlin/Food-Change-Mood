package org.berlin.presentation

import org.berlin.logic.IngredientGame
import org.berlin.model.GameState
import org.berlin.model.MealForIngredientGame

class FoodChangeMoodUI(
    private val ingredientGame: IngredientGame
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            1 -> printFakeUseCase()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun ingredientGameUseCase() {
        while (ingredientGame.getState() == GameState.RUNNING) {
            val gameCurrentMeal: MealForIngredientGame = ingredientGame.getMeal()!!

            println("Meal Name : ${gameCurrentMeal.mealName}")
            println("Ingredients")
            println(" 1 --> ${gameCurrentMeal.threeIngredientOnlyOneCorrect[0]}")
            println(" 2 --> ${gameCurrentMeal.threeIngredientOnlyOneCorrect[1]}")
            println(" 3 --> ${gameCurrentMeal.threeIngredientOnlyOneCorrect[2]}")
            print("Choose The Number Of Correct Ingredient")
            val input = getUserInput()?:return
            when (input) {
                1 -> ingredientGame.checkAnswer(gameCurrentMeal.threeIngredientOnlyOneCorrect[0])
                2 -> ingredientGame.checkAnswer(gameCurrentMeal.threeIngredientOnlyOneCorrect[1])
                3 -> ingredientGame.checkAnswer(gameCurrentMeal.threeIngredientOnlyOneCorrect[2])
            }
        }
        when (ingredientGame.getState()) {
            GameState.WON -> {
                println("You Won your Score Is : ${ingredientGame.getScore()}")
                ingredientGame.reset()
            }

            GameState.LOST -> {
                println("You Lost your Score Is : ${ingredientGame.getScore()}")
                ingredientGame.reset()
            }

            else -> {
                return
            }
        }

    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
