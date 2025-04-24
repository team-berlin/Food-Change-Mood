package presentation.search

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
    private var viewer: Viewer = mockk(relaxed = true)
    private var reader: Reader = mockk(relaxed = true)
    private lateinit var searchMealsByNameUI: SearchMealsByNameUI

    @BeforeEach
    fun setup() {
        searchMealsByNameUseCase = mockk(relaxed = true)
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
        verify { viewer.display("You didn't type anything. Please enter a word to search") }
    }

    @Test
    fun `run should return the meals when they matching search word`() {
        //Given
        every { reader.getUserInput() } returns "cake"
        // When
        searchMealsByNameUI.run()

        // Then
        verify {
            viewer.display("cheese cake")
        }
    }

}
