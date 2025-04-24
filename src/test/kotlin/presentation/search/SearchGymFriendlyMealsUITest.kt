package presentation.search

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.search.SearchGymFriendlyMealsUseCase
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.search.SearchGymFriendlyMealsUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SearchGymFriendlyMealsUITest {
    private lateinit var gymFriendlyMeals: SearchGymFriendlyMealsUseCase
    private lateinit var searchGymFriendlyMealsUI: SearchGymFriendlyMealsUI
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        gymFriendlyMeals = mockk()
        searchGymFriendlyMealsUI = SearchGymFriendlyMealsUI(gymFriendlyMeals, reader, viewer)
    }

    @Test
    fun `should call viewer when the presentation start`() {

        // When
        searchGymFriendlyMealsUI.run()

        // Then
        verify { viewer.display(any()) }
    }

    @Test
    fun `should call reader when the presentation start`() {

        // When
        searchGymFriendlyMealsUI.run()

        // Then
        verify { reader.getUserInput() }
    }

    @ParameterizedTest
    @CsvSource(
        " ,200, , ",
        "10, , , "
    )
    fun `should display invalid input when the protein or calories input is null`(
        calories: String?,
        protein: String?,
        caloriesTolerance: String?,
        proteinTolerance: String?
    ) {

        // Given
        every { reader.getUserInput() } returnsMany listOf(
            calories,
            protein,
            caloriesTolerance,
            proteinTolerance
        )

        // When
        searchGymFriendlyMealsUI.run()

        // Then
        verify { viewer.display("Invalid input. Please enter numeric values.") }
    }

    @ParameterizedTest
    @CsvSource(
        "900,200, , ",
        "10,10,10,10"
    )
    fun `should display no meals found when no matching meals found`(
        calories: String,
        protein: String,
        caloriesTolerance: String?,
        proteinTolerance: String?
    ) {

        // Given
        every { reader.getUserInput() } returnsMany listOf(
            calories,
            protein,
            caloriesTolerance,
            proteinTolerance
        )

        every { gymFriendlyMeals.getMealsByCaloriesAndProtein(any()) } throws NoSuchElementException()

        // When
        searchGymFriendlyMealsUI.run()

        // Then
        verify { viewer.display("No meals found matching meals.") }
    }

    @Test
    fun `should display meals when found in getMealsByCaloriesAndProtein`() {

        // Given
        every { reader.getUserInput() } returns "10"
        every { gymFriendlyMeals.getMealsByCaloriesAndProtein(any()) } returns listOf(createMeal())

        // When
        searchGymFriendlyMealsUI.run()

        // Then
        verify { viewer.display("\nTotal meals found: 1") }
    }

}