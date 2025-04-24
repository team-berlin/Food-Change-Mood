package logic.usecase.retrieval

import common.generatePotatoMeals
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.retrieval.GetMealsContainsPotatoUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetMealsContainsPotatoUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk()
        getMealsContainsPotatoUseCase = GetMealsContainsPotatoUseCase(mealsRepository)
    }

    @Test
    fun `Should return list with size equals 10 meals that contains potato in its ingredients`() {
        // Given
        every { mealsRepository.getAllMeals() } returns generatePotatoMeals(10)

        // When
        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato()

        // Then
        assertEquals(result.size, 10)
    }

    @Test
    fun `Should return list with size equals 10 meals that contains potato in its ingredients even if there is more than 10 items`() {
        // Given
        every { mealsRepository.getAllMeals() } returns generatePotatoMeals(15)

        // When
        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato()

        // Then
        assertEquals(result.size, 10)
    }

    @Test
    fun `Should return list of meals that contains potato in its ingredients even if the list has less than 10 items`() {
        // Given
        every { mealsRepository.getAllMeals() } returns generatePotatoMeals(3)

        // When
        val result = getMealsContainsPotatoUseCase.getMealsContainsPotato()

        // Then
        assertEquals(result.size, 3)
    }

    @Test
    fun `should return exception when the list is empty`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When && Then
        assertThrows<NoSuchElementException> {
            getMealsContainsPotatoUseCase.getMealsContainsPotato()
        }
    }

}