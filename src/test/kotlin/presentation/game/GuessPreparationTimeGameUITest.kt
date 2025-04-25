package presentation.game

import io.mockk.*
import logic.usecase.game.GuessPreparationTimeGameUseCase
import org.berlin.model.GameState
import org.berlin.presentation.game.GuessPreparationTimeGameUI
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GuessPreparationTimeGameUITest {
    private lateinit var guessPreparationTimeGameUI: GuessPreparationTimeGameUI
    private lateinit var guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase
    private lateinit var reader: Reader
    private lateinit var viewer: Viewer

    @BeforeEach
    fun setup() {
        reader = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        guessPreparationTimeGameUseCase = mockk()
        guessPreparationTimeGameUI = GuessPreparationTimeGameUI(guessPreparationTimeGameUseCase, reader, viewer)
    }

    @Test
    fun `run should call playGame method`() {
        // Given
        val mealName = "Pizza"
        val mealTime = 15

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, false)
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.RUNNING

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { guessPreparationTimeGameUseCase.run() }
    }

    @Test
    fun `playGame should prompt user for meal preparation time guess`() {
        // Given
        val mealName = "Burger"
        val mealTime = 10

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, false)
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.RUNNING

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { viewer.display("Guess the preparation time of Burger meal") }
    }

    @Test
    fun `playGame should handle invalid input`() {
        // Given
        val mealName = "Pasta"
        val mealTime = 20

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, false)
        every { reader.getUserInput() } returnsMany listOf("invalid")
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.RUNNING

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { viewer.display("Please enter a valid input") }
    }

    @Test
    fun `playGame should display correct message when user makes a correct guess`() {
        // Given
        val mealName = "Salad"
        val mealTime = 5

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, false)
        every { reader.getUserInput() } returns "5"
        every { guessPreparationTimeGameUseCase.evaluateGuess(5) } returns GuessPreparationTimeGameUseCase.GuessResult.CORRECT
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.WON

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { viewer.display("CORRECT") }
    }

    @Test
    fun `showFinalResult should display win message when game is won`() {
        // Given
        val mealName = "Sandwich"
        val mealTime = 7

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, false)
        every { reader.getUserInput() } returns "7"
        every { guessPreparationTimeGameUseCase.evaluateGuess(7) } returns GuessPreparationTimeGameUseCase.GuessResult.CORRECT
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.WON

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { viewer.display("Great Job! It takes 7 minutes") }
    }

    @Test
    fun `showFinalResult should display lost message when game is lost`() {
        // Given
        val mealName = "Steak"
        val mealTime = 30

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, false)
        every { reader.getUserInput() } returns "20"
        every { guessPreparationTimeGameUseCase.evaluateGuess(20) } returns GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW
        every { reader.getUserInput() } returns "20"
        every { guessPreparationTimeGameUseCase.evaluateGuess(20) } returns GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW
        every { reader.getUserInput() } returns "20"
        every { guessPreparationTimeGameUseCase.evaluateGuess(20) } returns GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.LOST

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { viewer.display("The time it takes to prepare Steak meal is 30 minutes") }
    }

    @Test
    fun `getUserGuess should handle null input`() {
        // Given
        val mealName = "Soup"
        val mealTime = 15

        every { guessPreparationTimeGameUseCase.run() } just runs
        every { guessPreparationTimeGameUseCase.getCurrentMeal() } returns Pair(mealName, mealTime)
        every { guessPreparationTimeGameUseCase.isRunning() } returnsMany listOf(true, true, false)
        every { reader.getUserInput() } returnsMany listOf(null)
        every { guessPreparationTimeGameUseCase.getGameState() } returns GameState.RUNNING

        // When
        guessPreparationTimeGameUI.run()

        // Then
        verify { viewer.display("Please enter a valid input") }
    }
}