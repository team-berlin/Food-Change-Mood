package logic.usecase.suggest

import common.genarateEasyMeals
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.suggest.SuggestEasyFoodUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestEasyFoodUseCaseTest {
    private lateinit var mealRepository: MealsRepository
    private lateinit var easyFoodUseCase: SuggestEasyFoodUseCase

    @BeforeEach
    fun setup() {
        mealRepository = mockk<MealsRepository>()
        easyFoodUseCase = SuggestEasyFoodUseCase(mealRepository)
    }

    @Test
    fun `getEasyFoodSuggestion should return all list when is less than random number`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(7)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(7, result.size)

    }

    @Test
    fun `getEasyFood should return easy random meals when filter list is equal random number`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(10)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(result.size, 10)

    }

    @Test
    fun `getEasyFood should throws NoSuchElementException when filter list is empty`() {
        every { mealRepository.getAllMeals() } returns emptyList()

        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()

        }
    }

    @Test
    fun `getEasyFoodSuggestion should return just 10 easy random meals when list more than random number `() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(20)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(10, result.size)

    }

    @Test
    fun `getEasyFoodSuggestion should return unique meals when random item selected`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(10)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(result.distinctBy { it.id }.size, result.size)

    }
}