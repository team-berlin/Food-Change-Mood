package logic.usecase.suggest

import createMeals
import fake.FakeMealsRepository
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.suggest.SuggestEasyFoodUseCase
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Disabled
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
        //Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeals(0, "random easy meal", 30, 5, 5),
            createMeals(1, "random easy meal", 30, 5, 5),
            createMeals(2, "random easy meal", 20, 1, 1),
            createMeals(3, "random easy meal", 10, 5, 4),
            createMeals(4, "random easy meal", 15, 3, 2),
            createMeals(5, "random easy meal", 30, 5, 5),
            createMeals(6, "random easy meal", 20, 1, 1)
        )
        //when
        val result = easyFoodUseCase.getEasyFoodSuggestion()
        //then
        assertEquals(7, result.size)

    }

    @Test
    fun `getEasyFood should return easy random meals when filter list is equal random number`() {
        //Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeals(0, "random easy meal", 30, 5, 5),
            createMeals(1, "random easy meal", 30, 5, 5),
            createMeals(2, "random easy meal", 20, 1, 1),
            createMeals(3, "random easy meal", 10, 5, 4),
            createMeals(4, "random easy meal", 15, 3, 2),
            createMeals(5, "random easy meal", 30, 5, 5),
            createMeals(6, "random easy meal", 20, 1, 1),
            createMeals(7, "random easy meal", 10, 5, 4),
            createMeals(5, "random easy meal", 15, 3, 2),
            createMeals(9, "random easy meal", 30, 5, 5)
        )

        //when
        val result = easyFoodUseCase.getEasyFoodSuggestion()

        //then
        assertEquals(result.size, 10)

    }

    @Test
    fun `getEasyFood should throws NoSuchElementException when filter list is empty`() {
        //Given
        every { mealRepository.getAllMeals() } returns emptyList()

        //then
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()

        }
    }

    @Test
    fun `getEasyFoodSuggestion should return just 10 easy random meals when list more than random number `() {
        //Given
        every { mealRepository.getAllMeals() } returns listOf(
            createMeals(0, "random easy meal", 30, 5, 5),
            createMeals(1, "random easy meal", 30, 5, 5),
            createMeals(2, "random easy meal", 20, 1, 1),
            createMeals(3, "random easy meal", 10, 5, 4),
            createMeals(4, "random easy meal", 15, 3, 2),
            createMeals(5, "random easy meal", 30, 5, 5),
            createMeals(6, "random easy meal", 20, 1, 1),
            createMeals(7, "random easy meal", 10, 5, 4),
            createMeals(5, "random easy meal", 15, 3, 2),
            createMeals(9, "random easy meal", 30, 5, 5),
            createMeals(10, "random easy meal", 30, 5, 5),
            createMeals(11, "random easy meal", 30, 5, 5),

        )

        //when
        val result = easyFoodUseCase.getEasyFoodSuggestion()

        //then
        assertEquals(10, result.size)

    }

    @Test
    fun `getEasyFoodSuggestion should return unique meals when random item selected`() {
        val fakeRepository = FakeMealsRepository()
        val easyFoodUseCase = SuggestEasyFoodUseCase(fakeRepository)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(result.size, result.distinctBy { it.id }.size)


    }
}