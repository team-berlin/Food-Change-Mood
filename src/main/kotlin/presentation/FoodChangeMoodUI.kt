package org.berlin.presentation

import org.berlin.logic.GymHelperUseCase

class FoodChangeMoodUI(
    private val gymHelperUseCase: GymHelperUseCase
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
            9 -> launchGymHelper()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun launchGymHelper () {
        println("Please enter the number of calories:")
        val caloriesInput = readLine()?.toDoubleOrNull()
        println("Please enter the amount of protein:")
        val proteinInput = readLine()?.toDoubleOrNull()

        if (caloriesInput == null || proteinInput == null) {
            println("Invalid input. Please enter numeric values.")
            return
        }

        val meals = gymHelperUseCase.getMealsByCaloriesAndProtein(
            calories = caloriesInput,
            protein = proteinInput
        )

        if (meals.isEmpty()) {
            println("No meals found matching meals.")
        } else {
            println("Found meals: ")
            meals.forEach { meal ->
                println(meal)
            }
        }
    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("9- Use gym helper to search for meals by calories and proteins ")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
