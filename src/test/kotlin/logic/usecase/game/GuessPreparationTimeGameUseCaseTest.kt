package logic.usecase.game

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.GameState
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GuessPreparationTimeGameUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var guessPreparationTimeGameUseCase: GuessPreparationTimeGameUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        guessPreparationTimeGameUseCase = GuessPreparationTimeGameUseCase(mealsRepository)
    }

    @Test
    fun `should return correct meal name`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(createMeal(name = "Spaghetti"))

        // When
        val mealName = guessPreparationTimeGameUseCase.getRandomMeal().name

        // Then
        assertThat(mealName).isEqualTo("Spaghetti")
    }

    @Test
    fun `should return correct meal preparation time`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(createMeal(minutes = 20))

        // When
        val mealTime = guessPreparationTimeGameUseCase.getRandomMeal().minutes

        // Then
        assertThat(mealTime).isEqualTo(20)
    }

    @Test
    fun `should return correct meal name and preparation time`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(createMeal(name = "Spaghetti", minutes = 20))

        // When
        val mealName = guessPreparationTimeGameUseCase.getRandomMeal().name
        val mealTime = guessPreparationTimeGameUseCase.getRandomMeal().minutes

        // Then
        assertEquals(mealName to mealTime, "Spaghetti" to 20)
    }

    @Test
    fun `should return random meal name from list`() {
        // Given
        val meals = listOf(
            createMeal(name = "Pizza", minutes = 15),
            createMeal(name = "Burger", minutes = 10),
            createMeal(name = "Pasta", minutes = 25)
        )
        every { mealsRepository.getAllMeals() } returns meals

        // When
        val mealName = guessPreparationTimeGameUseCase.getRandomMeal().name

        // Then
        assertThat(meals.map { it.name }).contains(mealName)
    }

    @Test
    fun `should return random meal preparation time from list`() {
        // Given
        val meals = listOf(
            createMeal(name = "Pizza", minutes = 15),
            createMeal(name = "Burger", minutes = 10),
            createMeal(name = "Pasta", minutes = 25)
        )
        every { mealsRepository.getAllMeals() } returns meals

        // When
        val mealTime = guessPreparationTimeGameUseCase.getRandomMeal().minutes

        // Then
        assertThat(meals.map { it.minutes }).contains(mealTime)
    }

    @Test
    fun `should throw exception when no meals found`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When && Then
        assertThrows<NoSuchElementException> {
            guessPreparationTimeGameUseCase.getRandomMeal()
        }
    }

    @Test
    fun `run should initialize game with random meal`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)

        // When
        guessPreparationTimeGameUseCase.run()

        // Then
        assertThat(guessPreparationTimeGameUseCase.getCurrentMeal().first).isEqualTo("Pizza")
        assertThat(guessPreparationTimeGameUseCase.getCurrentMeal().second).isEqualTo(15)
        assertThat(guessPreparationTimeGameUseCase.remainingAttempts).isEqualTo(1)
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.RUNNING)
    }

    @Test
    fun `evaluateGuess should return CORRECT when guess matches preparation time`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When
        val result = guessPreparationTimeGameUseCase.evaluateGuess(15)

        // Then
        assertThat(result).isEqualTo(GuessPreparationTimeGameUseCase.GuessResult.CORRECT)
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.WON)
    }

    @Test
    fun `evaluateGuess should return TOO_LOW when guess is less than preparation time`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When
        val result = guessPreparationTimeGameUseCase.evaluateGuess(10)

        // Then
        assertThat(result).isEqualTo(GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW)
        assertThat(guessPreparationTimeGameUseCase.remainingAttempts).isEqualTo(2)
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.RUNNING)
    }

    @Test
    fun `evaluateGuess should return TOO_HIGH when guess is more than preparation time`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When
        val result = guessPreparationTimeGameUseCase.evaluateGuess(20)

        // Then
        assertThat(result).isEqualTo(GuessPreparationTimeGameUseCase.GuessResult.TOO_HIGH)
        assertThat(guessPreparationTimeGameUseCase.remainingAttempts).isEqualTo(2)
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.RUNNING)
    }

    @Test
    fun `evaluateGuess should set game state to LOST after exceeding maximum attempts`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When
        guessPreparationTimeGameUseCase.evaluateGuess(10)
        guessPreparationTimeGameUseCase.evaluateGuess(10)
        val result = guessPreparationTimeGameUseCase.evaluateGuess(10)

        // Then
        assertThat(result).isEqualTo(GuessPreparationTimeGameUseCase.GuessResult.TOO_LOW)
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.LOST)
    }

    @Test
    fun `isRunning should return true when game state is RUNNING`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When & Then
        assertThat(guessPreparationTimeGameUseCase.isRunning()).isTrue()
    }

    @Test
    fun `isRunning should return false when game state is WON`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()
        guessPreparationTimeGameUseCase.evaluateGuess(15)

        // When & Then
        assertThat(guessPreparationTimeGameUseCase.isRunning()).isFalse()
    }

    @Test
    fun `isRunning should return false when game state is LOST`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        guessPreparationTimeGameUseCase.evaluateGuess(10)
        guessPreparationTimeGameUseCase.evaluateGuess(10)
        guessPreparationTimeGameUseCase.evaluateGuess(10)

        // When & Then
        assertThat(guessPreparationTimeGameUseCase.isRunning()).isFalse()
    }

    @Test
    fun `getCurrentMeal should return correct meal name and preparation time`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When
        val result = guessPreparationTimeGameUseCase.getCurrentMeal()

        // Then
        assertThat(result.first).isEqualTo("Pizza")
        assertThat(result.second).isEqualTo(15)
    }

    @Test
    fun `getCurrentMeal should throw exception when no meal is set`() {
        // When & Then
        assertThrows<NoSuchElementException> {
            guessPreparationTimeGameUseCase.getCurrentMeal()
        }
    }

    @Test
    fun `getGameState should return RUNNING after initialization`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        // When & Then
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.RUNNING)
    }

    @Test
    fun `getGameState should return WON after correct guess`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()
        guessPreparationTimeGameUseCase.evaluateGuess(15)

        // When & Then
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.WON)
    }

    @Test
    fun `getGameState should return LOST after maximum incorrect attempts`() {
        // Given
        val meal = createMeal(name = "Pizza", minutes = 15)
        every { mealsRepository.getAllMeals() } returns listOf(meal)
        guessPreparationTimeGameUseCase.run()

        guessPreparationTimeGameUseCase.evaluateGuess(10)
        guessPreparationTimeGameUseCase.evaluateGuess(10)
        guessPreparationTimeGameUseCase.evaluateGuess(10)

        // When & Then
        assertThat(guessPreparationTimeGameUseCase.getGameState()).isEqualTo(GameState.LOST)
    }

}