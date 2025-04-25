package presentation.game

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.game.IngredientGameUseCase
import org.berlin.model.MealForIngredientGame
import org.berlin.presentation.game.IngredientGameUI
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class IngredientGameUITest {
    private val ingredientGame: IngredientGameUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)
    private lateinit var ingredientGameUI: IngredientGameUI

    @BeforeEach
    fun setup() {
        every { ingredientGame.isRunning() } returnsMany listOf(true, false)
        ingredientGameUI = IngredientGameUI(ingredientGame, reader, viewer)
    }

    @Test
    fun `run should call ingredient game useCase run`() {
        every { reader.getUserInput() } returns "1"
        //when
        ingredientGameUI.run()
        //then
        verify(exactly = 1) { ingredientGame.run() }
    }

    @Test
    fun `run should display meal name 1 times ,ingredients 4 ,choose message to user 1 ,and final score 1 `() {
        //given
        every { ingredientGame.getCurrentMeal() } returns MealForIngredientGame("Meal", "A", listOf("A", "B", "C"))
        every { reader.getUserInput() } returns "1"
        //when
        ingredientGameUI.run()
        //then
        verify(exactly = 7) { viewer.display(any()) }
    }

    @Test
    fun `run should display meal name valid`() {
        //given
        every { ingredientGame.getCurrentMeal() } returns MealForIngredientGame("Meal", "A", listOf("A", "B", "C"))
        every { reader.getUserInput() } returns "1"
        //when
        ingredientGameUI.run()
        //then
        verify { viewer.display("Meal Name : Meal") }
    }

    @Test
    fun `run should display meal ingredients valid`() {
        //given
        val meal = MealForIngredientGame("Meal", "A", listOf("A", "B", "C"))
        every { ingredientGame.getCurrentMeal() } returns meal
        every { reader.getUserInput() } returns "1"
        //when
        ingredientGameUI.run()
        //then
        verify {
            meal.threeIngredientOnlyOneCorrect.forEachIndexed { index, ingredient ->
                viewer.display("${index + 1} --> $ingredient")
            }
        }
    }

    @Test
    fun `run should submit user answer when is valid`() {
        //given
        every { reader.getUserInput() } returns "1"
        //when
        ingredientGameUI.run()
        //then
        verify { ingredientGame.submitUserAnswer(any()) }
    }

    @Test
    fun `run should not submit answer when user invalid 0`() {
        //given
        every { reader.getUserInput() } returns "0"
        //when
        ingredientGameUI.run()
        //then
        verify(exactly = 0) { ingredientGame.submitUserAnswer(any()) }
    }

    @Test
    fun `run should display exception message when use case throw exception`() {
        //given
        every { ingredientGame.run() } throws Exception("mm")
        //when
        ingredientGameUI.run()
        //then
        verify(exactly = 1) { viewer.display("mm") }
    }

    @Test
    fun `run should do recursive when try to get match input and submit only valid input 1`() {
        //given
        every { reader.getUserInput() } returnsMany listOf(null, "-1", "q", "w", "b", "g", "66", null, "fff", "1")
        //when
        ingredientGameUI.run()
        //then
        verify { ingredientGame.submitUserAnswer(1) }
    }

}