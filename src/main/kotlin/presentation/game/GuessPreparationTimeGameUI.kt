package org.berlin.presentation.game

import logic.usecase.game.GuessPreparationTimeGameUseCase
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader

class GuessPreparationTimeGameUI(
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase,
    private val reader: Reader
): UiRunner {
    override val id: Int = 5
    override val label: String = "Guess Preparation Time Game"
    override fun run() {
        var isCorrect = false
        val randomMeal = guessPreparationTimeGameUseCase.getRandomMeal()
        val mealName = randomMeal.name
        val correctPreparationTime = randomMeal.minutes
        var guessPreparationTime: Int?
        var attempts = 3
        println("Guess the preparation time of $mealName meal")
        while (attempts-- > 0) {
            try {
                guessPreparationTime = reader.getUserInput()?.toIntOrNull()
                if (correctPreparationTime > guessPreparationTime!!) {
                    println("too low, try again")
                } else if (correctPreparationTime < guessPreparationTime) {
                    println("too high, try again")
                } else {
                    println("Great Job!, it takes $guessPreparationTime minutes")
                    isCorrect = true
                    break
                }
            } catch (_: Exception) {
                attempts++
                println("Please enter a valid input")
            }
        }
        if (!isCorrect) {
            println("The time it takes to prepare $mealName meal is $correctPreparationTime minutes")
        }
    }
}