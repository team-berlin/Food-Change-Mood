package org.berlin.presentation.game

import logic.usecase.game.GuessPreparationTimeGameUseCase
import org.berlin.model.GameState
import org.berlin.presentation.UiRunner
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer

class GuessPreparationTimeGameUI(
    private val guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase,
    private val reader: Reader,
    private val viewer: Viewer
) : UiRunner {

    override val id: Int = 5
    override val label: String = "Guess Preparation Time Game"

    override fun run() {
        playGame()
    }

    private fun playGame() {
        guessPreparationTimeGameUseCase.run()
        val mealName = guessPreparationTimeGameUseCase.getMeal().first
        val correctTime = guessPreparationTimeGameUseCase.getMeal().second

        viewer.display("Guess the preparation time of $mealName meal")

        while (guessPreparationTimeGameUseCase.isRunning()) {
            val userGuess = getUserGuess()

            if (userGuess == null) {
                viewer.display("Please enter a valid input")
                continue
            }

            viewer.display(guessPreparationTimeGameUseCase.evaluateGuess(userGuess).toString())

        }
        showFinalResult(guessPreparationTimeGameUseCase.getGameState(), mealName, correctTime)

    }

    private fun getUserGuess(): Int? = reader.getUserInput()?.toIntOrNull()

    private fun showFinalResult(gameState: GameState, mealName: String, correctTime: Int) {
        when (gameState) {
            GameState.LOST -> viewer.display("The time it takes to prepare $mealName meal is $correctTime minutes")
            GameState.WON -> viewer.display("Great Job! It takes $correctTime minutes")
            else -> {}
        }
    }


}