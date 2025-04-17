package org.berlin.presentation

import org.berlin.logic.usecase.SuggestItalianFoodForLargeGroupUseCase

class FoodChangeMoodUI(
    private val suggestItalianFoodForLargeGroupUseCase:
    SuggestItalianFoodForLargeGroupUseCase
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()
        println()
        when (input) {
            1 -> printFakeUseCase()
            15 -> getItalianMealsForLargeGroup()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("15 - Get Italian Meals For Large Group")
        print("Here: ")
    }

    private fun getItalianMealsForLargeGroup() {

        val meals = suggestItalianFoodForLargeGroupUseCase
            .suggestItalianMealsForLargeGroup()
        meals.forEach {
            println("Italian Meal Name: ${it.name}\n")
        }
    }



    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
