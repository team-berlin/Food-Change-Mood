package presentation.search

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.search.SearchFoodByCultureUseCase
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.search.SearchFoodByCultureUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchFoodByCultureUITest {
    private lateinit var exploreFoodCulture: SearchFoodByCultureUseCase
    private lateinit var searchFoodByCultureUI: SearchFoodByCultureUI
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        exploreFoodCulture = mockk()
        searchFoodByCultureUI = SearchFoodByCultureUI(exploreFoodCulture, viewer, reader)
    }

    @Test
    fun `should call viewer when the presentation start`() {
        // When
        searchFoodByCultureUI.run()

        // Then
        verify { viewer.display(any()) }
    }

    @Test
    fun `should call reader when the presentation start`() {
        // When
        searchFoodByCultureUI.run()

        // Then
        verify { reader.getUserInput() }
    }


    @Test
    fun `should display invalid input when the country input is null`() {
        // Given
        every { reader.getUserInput() } returns null

        //When
        searchFoodByCultureUI.run()

        //Then
        verify { viewer.display("Please enter a valid country name.") }
    }

    @Test
    fun `should return error message when use case throw exception `() {

        //Given
        val countryName = "malysia"
        every { reader.getUserInput() } returns countryName
        every { exploreFoodCulture.exploreFoodByCountry(countryName) } throws RuntimeException()

        //When
        searchFoodByCultureUI.run()

        //Then
        verify { viewer.display("️Something went wrong while searching for \"$countryName\".") }
    }

    @Test
    fun `should display empty list when no meals found for a country`() {
        //Given
        val countryName = "amr"
        every { reader.getUserInput() } returns countryName
        every { exploreFoodCulture.exploreFoodByCountry(countryName) } returns emptyList()

        //When
        searchFoodByCultureUI.run()

        //Then
        verify { viewer.display("\"$countryName\" is not found in any fields.") }
    }

    @Test
    fun `should display meals when found in exploreFoodByCountry`() {
        // Given
        val countryName = "Japan"
        val mealName = "Kofta"

        every { reader.getUserInput() } returns countryName
        every { exploreFoodCulture.exploreFoodByCountry(countryName) } returns listOf(createMeal(name = mealName))

        // When
        searchFoodByCultureUI.run()

        // Then
        verify { viewer.display("\nFound 1 meals related to \"$countryName\":") }
    }

}