package org.berlin.presentation

import org.berlin.logic.GetSeafoodMealsUseCase

class FoodChangeMoodUI(
    private val getSeafoodMealsUseCase: GetSeafoodMealsUseCase,
) {

    fun start() {
        showWelcome()
        presentFeatures()
    }

    private fun presentFeatures() {
        showOptions()
        val input = getUserInput()

        when (input) {
            1 -> launchSeafoodMealsUseCase()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun launchSeafoodMealsUseCase() {
       getSeafoodMealsUseCase.getSeafoodMeals().forEach {seafoodMeal->
           println(seafoodMeal)
       }
    }

    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get a list of all seafood meals")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
