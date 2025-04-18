package org.berlin.presentation

import org.berlin.logic.ingredient_game.InvalidInputForIngredientGameException

class FoodChangeMoodUI(
    private val ingredientGame: IngredientGameInteractor
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
            11 -> ingredientGameUseCase()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun ingredientGameUseCase() {
        try {
            ingredientGame.run()
            while (ingredientGame.isRunning()) {
                println("Meal Name : ${ingredientGame.getCurrentMealName()}")
                println("Ingredients : ")
                ingredientGame.getCurrentIngredients()
                    .forEachIndexed { i, ingredient -> println("${i + 1}--> $ingredient") }
                print("Choose The Number Of Correct Ingredient : ")
                ingredientGame.submitAnswer(getUserInput() ?: return)
                println()
            }
            println(ingredientGame.getTurnResult())
        } catch (e: InvalidInputForIngredientGameException) {
            println(e.message)
        } catch (e: Exception) { println(e.message) }

    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("11 - Ingredient Game")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
