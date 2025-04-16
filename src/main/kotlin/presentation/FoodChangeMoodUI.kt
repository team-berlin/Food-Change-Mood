package org.berlin.presentation

import org.berlin.logic.usecase.EasyFoodSuggestionUseCase

class FoodChangeMoodUI(
    private val easyFoodSuggestionRepository: EasyFoodSuggestionUseCase
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
            2 -> easyFoodSuggestion()
            else -> println("Invalid Input")
        }

        presentFeatures()
    }

    private fun printFakeUseCase() {
        println("UseCase successfully done...!")
    }

    private fun easyFoodSuggestion() {
        val meal = easyFoodSuggestionRepository.getEasyFoodSuggestion()
        meal.forEach { println(it) }

    }
    private fun showWelcome() {
        println("Welcome to cost of living app")
    }

    private fun showOptions() {
        println("\n\n=== Please enter one of the following numbers ===")
        println("1 - Get fake UseCase for testing")
        println("2 - Get easy food suggestion")
        print("Here: ")
    }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
