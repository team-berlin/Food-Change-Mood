package logic.usecase.game

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.junit.jupiter.api.Assertions.*
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
}