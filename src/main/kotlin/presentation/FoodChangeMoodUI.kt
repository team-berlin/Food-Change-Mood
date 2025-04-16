package org.berlin.presentation

import org.berlin.logic.GetMealsContainsPotatoUseCase

class FoodChangeMoodUI(
    private val getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase,
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
            12 -> launchRandomPotatoesMeals()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun showWelcome() {
        println("Welcome to Food Change Mood app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("12- Get names of 10 meals that contains potatoes in its ingredients")
        print("Here: ")
    }

    private fun launchRandomPotatoesMeals(){
        getMealsContainsPotatoUseCase.getMealsContainsPotato().forEach { println(it) }
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
