package logic.usecase.suggest

import common.genarateEasyMeals
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.suggest.SuggestEasyFoodUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
        verify { mealRepository.getAllMeals() }

    }

    @Test
    fun `getEasyFoodSuggestion should return easy random meals when filter list is equal random number`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(10)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(10, result.size)
        verify { mealRepository.getAllMeals() }

    }

    @Test
    fun `getEasyFoodSuggestion should throws NoSuchElementException when filter list is empty`() {
        every { mealRepository.getAllMeals() } returns emptyList()

        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()

        }

        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFoodSuggestion should return throw NoSuchElementException when filter list has only minutes condition matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(5, 30, 15, 10)

        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFoodSuggestion should return throw NoSuchElementException when filter list has only number of ingredients condition matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(5, 60, 4, 10)

        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when filter list has only number of steps condition match`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(5, 60, 14, 5)

        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return easy meals when all filter list is match with all conditions`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 30, 5, 5
        )
        val result = easyFoodUseCase.getEasyFoodSuggestion()
        assertTrue { result.isNotEmpty() }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when minutes and ingredients condition  is matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 30, 5, 20
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when minutes and number of steps condition is matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 30, 15, 5
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when number of ingredients and step condition is matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 60, 5, 5
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when number of ingredients and step condition is not matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 30, 15, 15
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when number of step and minutes condition is not matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 60, 5, 15
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when number of minutes and step condition is not matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 60, 5, 15
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }

    @Test
    fun `getEasyFood should return throw NoSuchElementException when number of ingredients and minutes condition is not matched`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(
            1, 60, 5, 5
        )
        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }
        verify { mealRepository.getAllMeals() }
    }


    @Test
    fun `getEasyFoodSuggestion should return just 10 easy random meals when list more than random number `() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(20)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(10, result.size)
        verify { mealRepository.getAllMeals() }

    }

    @Test
    fun `getEasyFoodSuggestion should return unique meals when random item selected`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(10)

        val result = easyFoodUseCase.getEasyFoodSuggestion()

        assertEquals(result.distinctBy { it.id }.size, result.size)
        verify { mealRepository.getAllMeals() }

    }

    @Test
    fun `getEasyFoodSuggestion should throw NoSuchElementException when filter results in empty list`() {
        every { mealRepository.getAllMeals() } returns genarateEasyMeals(5, 60, 10, 15)

        assertThrows<NoSuchElementException> {
            easyFoodUseCase.getEasyFoodSuggestion()
        }

        verify { mealRepository.getAllMeals() }
    }

}