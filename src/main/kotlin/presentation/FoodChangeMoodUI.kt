package org.berlin.presentation

import org.berlin.logic.IdentifyIraqiMealsUseCase


class FoodChangeMoodUI(
    private val identifyIraqiMealsUseCase : IdentifyIraqiMealsUseCase
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
            2 -> identifyIraqiMeals()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun identifyIraqiMeals() {
        println(identifyIraqiMealsUseCase.identifyIraqiMeals().toString())
    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("2 - Identify Iraqi Meals")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
