package logic.usecase.search

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import logic.usecase.helper.SelectionOfSearchAlgorithms
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class SearchMealsByNameUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var searchAlgorithm: SelectionOfSearchAlgorithms
    private lateinit var searchMealsByName: SearchMealsByNameUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk<MealsRepository>(relaxed = true)
        searchAlgorithm = mockk()
        searchMealsByName = SearchMealsByNameUseCase(mealsRepository, searchAlgorithm)
    }

    @Test
    fun `searchMealsByName should return the meals when they matching the search word`() {
        //Given
        val meals = listOf(
            createMeal(name = "cheese cake"),
            createMeal(name = "cheese pizza"),
        )
        every { mealsRepository.getAllMeals() } returns meals
        every { searchAlgorithm.search("cheese pizza", "pizza") } returns true
        every { searchAlgorithm.search("cheese cake", "pizza") } returns false

        //when
        val result = searchMealsByName.searchMealsByName("pizza")

        //Then
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].name).isEqualTo("cheese pizza")
    }

    @Test
    fun `searchMealsByName should return 0 when there is no matching with the search word`() {
        //Given
        val meals = listOf(
            createMeal(name = "cheese cake"),
            createMeal(name = "cheese pizza"),
        )
        every { mealsRepository.getAllMeals() } returns meals
        every { searchAlgorithm.search(any(), any()) } returns false

        //when && Then
        assertThrows<NoSuchElementException> {
            searchMealsByName.searchMealsByName("pizza")
        }
    }

}