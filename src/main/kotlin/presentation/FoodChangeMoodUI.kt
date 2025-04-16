package org.berlin.presentation

import org.berlin.logic.GuessPreparationTimeGameUseCase

class FoodChangeMoodUI(
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase
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
            5 -> launchGuessPreparationTimeGame()
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
        println("5 - Guess preparation time game")
        print("Here: ")
    }

    private fun launchGuessPreparationTimeGame(){
        var isCorrect = false
        val meal = guessPreparationTimeGameUseCase.guessPreparationTime()
        val minutes = meal.minutes
        val mealName = meal.name
        println("Guess the preparation time of $mealName meal")
        var preparationTime: Int?
        var attempts = 3
        while (attempts-- > 0){
            try {
                preparationTime = getUserInput()
                if (minutes > preparationTime!!) {
                    println("too low, try again")
                } else if (minutes < preparationTime) {
                    println("too high, try again")
                } else {
                    println("Great Job!, it takes $preparationTime minutes")
                    isCorrect = true
                    break
                }
            }catch (exception: Exception){
                println("Please enter a valid input")
            }
        }
        if (!isCorrect){
            println("The time it takes to prepare $mealName meal is $minutes minutes")
            }
        }

    private fun getUserInput(): Int? {
        return readLine()?.toIntOrNull()
    }
}
