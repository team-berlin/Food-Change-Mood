package presentation

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.usecase.game.IngredientGameUseCase
import org.berlin.logic.usecase.helper.EmptyMealsException
import org.berlin.logic.usecase.helper.InvalidChoiceException
import org.berlin.logic.usecase.helper.MealsNotEnoughException
import org.berlin.model.MealForIngredientGame
import org.berlin.presentation.IngredientGameInteractor
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class IngredientGameInteractorTest {
    private val ingredientGame = mockk<IngredientGameUseCase>()


    @Test
    fun `run should throw MealsNotEnoughException when meals is not match max questions`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns INVALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When // Then
        assertThrows<MealsNotEnoughException> {
            interactor.run()
        }
    }

    @Test
    fun `run should initialize game with 15 meals and reset state`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        // Then
        assertThat(interactor.isRunning()).isTrue()
    }

    @Test
    fun `run should initialize game with 15 meals and reset score`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        // Then
        assertThat(interactor.getScore()).isEqualTo(0)
    }

    @Test
    fun `run should initialize game with 15 meals and reset meals index`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        // Then
        assertThat(interactor.getCurrentMeal()).isEqualTo(VALID_MEALS[0])
    }


    @Test
    fun `submitUserAnswer should throw InvalidChoiceException when user choice 5 not in 1 , 2 , 3`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        // Then
        assertThrows<InvalidChoiceException> {
            interactor.submitUserAnswer(5)
        }
    }

    @Test
    fun `submitUserAnswer should throw InvalidChoiceException when user choice 0 not in 1 , 2 , 3`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        // Then
        assertThrows<InvalidChoiceException> {
            interactor.submitUserAnswer(0)
        }
    }


    @Test
    fun `submitUserAnswer should update state to lost when user choice wrong ingredient`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        interactor.submitUserAnswer(1)
        // Then
        assertThat(interactor.isRunning()).isFalse()

    }

    @Test
    fun `submitUserAnswer should increase score by 1000 when user choice correct ingredient`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        interactor.submitUserAnswer(2)
        // Then
        assertThat(interactor.getScore()).isEqualTo(1000)

    }

    @Test
    fun `submitUserAnswer should update state to won when user choice 15 times correct ingredient`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        choiceCorrectAnsMax(interactor)
        // Then
        assertThat(interactor.isRunning()).isFalse()

    }

    @Test
    fun `submitUserAnswer should update score to max score 15000 when user choice 15 times correct ingredient`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When
        interactor.run()
        choiceCorrectAnsMax(interactor)
        // Then
        assertThat(interactor.getScore()).isEqualTo(15000)

    }

    @Test
    fun `submitUserAnswer should throw IndexOutOfBoundsException when try to play more than max questions  `() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When // Then
        assertThrows<IndexOutOfBoundsException> {
            interactor.run()
            choiceCorrectAnsMax(interactor)
            choiceCorrectAnsMax(interactor)
        }

    }



    @Test
    fun `submitUserAnswer should throw EmptyMealsException when try to submit user input but method run not called`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When // Then
        assertThrows<EmptyMealsException> {
            interactor.submitUserAnswer(2)
        }

    }

    @Test
    fun `getCurrentMeal should throw EmptyMealsException when try to get current meal but method run not called`() {
        // Given
        every { ingredientGame.getFifteenMeals() } returns VALID_MEALS
        val interactor = IngredientGameInteractor(ingredientGame)
        // When // Then
        assertThrows<EmptyMealsException> {
            interactor.getCurrentMeal()
        }

    }



    private fun choiceCorrectAnsMax(interactor: IngredientGameInteractor) {
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
        interactor.submitUserAnswer(2)
    }


    companion object {
        val VALID_MEALS = List(15) {
            MealForIngredientGame("aaaa", "b", listOf("a", "b", "c"))
        }
        val INVALID_MEALS = List(14) {
            MealForIngredientGame("aaaa", "b", listOf("a", "b", "c"))
        }

    }
}