package logic.usecase.retrieval

import com.google.common.truth.Truth.assertThat
import common.meals
import common.seafoodMeals
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.retrieval.GetSeafoodMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class GetSeafoodMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var seafoodMealsUseCase: GetSeafoodMealsUseCase

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk(relaxed = true)
        seafoodMealsUseCase = GetSeafoodMealsUseCase(mealsRepository)
    }

    @Test
    fun `should return seafood meals sorted descending by protein value`() {
        //Given
        every { mealsRepository.getAllMeals() } returns seafoodMeals
        //when
        val result = seafoodMealsUseCase.getSeafoodMeals()
        //Then
        assertThat(result.map { it.name }).containsExactly("salmon pizza", "mussels", "tuna salad")
    }

    @Test
    fun `should throw exception when there is no seafood meals `() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()
        //When && Then
        assertThrows<NoSuchElementException> {
            seafoodMealsUseCase.getSeafoodMeals()
        }
    }

    @Test
    fun `should return only seafood meals`() {
        //Given
        every { mealsRepository.getAllMeals() } returns meals
        //when
        val result = seafoodMealsUseCase.getSeafoodMeals()
        //Then
        assertThat(result.map { it.name }).containsExactly("salmon pizza", "mussels", "tuna salad")
    }

    @ParameterizedTest
    @ValueSource(strings = arrayOf("SEAFOOD", "Seafood", "seafood", "SeaFOOD"))
    fun `should ignore case of seafood tag and return seafood meals`() {
        //Given
        every { mealsRepository.getAllMeals() } returns seafoodMeals
        //when
        val result = seafoodMealsUseCase.getSeafoodMeals()
        //Then
        assertThat(result.map { it.name }).containsExactly("salmon pizza", "mussels", "tuna salad")
    }
}