package org.berlin.presentation

import org.berlin.logic.ingredient_game.IngredientGameUseCase

class FoodChangeMoodUI(
    private val ingredientGameUseCase: IngredientGameUseCase
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
            11-> ingredientGameUseCase()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun ingredientGameUseCase() {
        ingredientGameUseCase.run()
        while (ingredientGameUseCase.isRunning()) {
            println("Meal Name : ${ingredientGameUseCase.getCurrentMealName()}")
            println("Ingredients")
            ingredientGameUseCase.getCurrentMealIngredients()
                .forEachIndexed{i,ingredient-> println("${i+1}--> $ingredient") }
            print("Choose The Number Of Correct Ingredient")
            ingredientGameUseCase.submitInput(getUserInput()?:return)
        }
        println(ingredientGameUseCase.getTurnResult())
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
