package presentation.search

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.search.SearchMealsByNameUseCase
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.search.SearchMealsByNameUI
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SearchMealsByNameUITest {
    private lateinit var searchMealsByNameUseCase: SearchMealsByNameUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var searchMealsByNameUI: SearchMealsByNameUI

    @BeforeEach
    fun setup() {
        searchMealsByNameUseCase = mockk(relaxed = true)
        viewer= mockk(relaxed = true)
        reader= mockk(relaxed = true)
        searchMealsByNameUI = SearchMealsByNameUI(searchMealsByNameUseCase, viewer, reader)

    }

    @Test
    fun `run should call viewer when the presentation start`() {

        // When
        searchMealsByNameUI.run()

        // Then
        verify { viewer.display(any()) }
    }

    @Test
    fun `run should call reader when the presentation start and the viewer display`() {

        // When
        searchMealsByNameUI.run()

        // Then
        verify { reader.getUserInput() }
    }

    @Test
    fun `run should display You didn't type anything Please enter a word to search when the search word is null`() {
        //Given
        every { reader.getUserInput() } returns null
        //when
        searchMealsByNameUI.run()
        //Then
        verify(exactly = 1) { viewer.display("You didn't type anything. Please enter a word to search") }
    }

    @Test
    fun `run should return the meals when they matching search word`() {
        //Given
        val searchWord=testWord
        val meals=testMeals
        every { searchMealsByNameUseCase.searchMealsByName(searchWord) } returns meals
        every { reader.getUserInput() } returns searchWord

        // When
        searchMealsByNameUI.run()

        // Then
        verify {
            meals.forEach { meal ->
                viewer.display(meal.name)
            }
        }
    }
    private companion object{
        val testMeals=listOf(
            createMeal(name="cheese cake"),
            createMeal(name="Orange cake")
        )
        val testWord="cake"


    }
}
