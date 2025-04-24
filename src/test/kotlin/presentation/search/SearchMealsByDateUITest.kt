package presentation.search

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.datetime.LocalDate
import org.berlin.logic.usecase.search.SearchMealsByDateUseCase
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.search.SearchMealsByDateUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SearchMealsByDateUITest {
    private lateinit var searchMealsByDateUseCase: SearchMealsByDateUseCase
    private lateinit var searchMealsByDateUI: SearchMealsByDateUI
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)

    @BeforeEach
    fun setup() {
        searchMealsByDateUseCase = mockk()
        searchMealsByDateUI = SearchMealsByDateUI(searchMealsByDateUseCase, reader, viewer)
    }

    @Test
    fun `should call viewer when the presentation starts`() {
        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display(any()) }
    }

    @Test
    fun `should call reader to get date input when the presentation starts`() {
        // Given
        every { reader.getUserInput() } returns "2023-01-01"

        // When
        searchMealsByDateUI.run()

        // Then
        verify { reader.getUserInput() }
    }

    @Test
    fun `should display error message when date input is empty`() {
        // Given
        every { reader.getUserInput() } returns ""

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display("Error: Date cannot be empty.") }
    }

    @Test
    fun `should display error message when date format is invalid`() {
        // Given
        every { reader.getUserInput() } returns "invalid-date"

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display(match { it.startsWith("Error:") }) }
    }

    @Test
    fun `should display no meals found message when no meals for the date`() {
        // Given
        val validDate = "2023-01-01"
        every { reader.getUserInput() } returns validDate
        every { searchMealsByDateUseCase.searchMealsByDate(any()) } throws NoSuchElementException("No meals found for date")

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display("Error: NoSuchElementException - No meals found for date") }
    }

    @Test
    fun `should display meals when found for the given date`() {
        // Given
        val validDate = "2023-01-01"
        val parsedDate = LocalDate.parse(validDate)
        val meal1 = createMeal(id = 1, name = "Meal 1", submissionDate = parsedDate)
        val meal2 = createMeal(id = 2, name = "Meal 2", submissionDate = parsedDate)
        val meals = listOf(meal1, meal2)

        every { reader.getUserInput() } returnsMany listOf(validDate, "no")
        every { searchMealsByDateUseCase.searchMealsByDate(parsedDate) } returns meals

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display("\nFound ${meals.size} meals added on $parsedDate:") }
        verify { viewer.display("ID: 1, Name: Meal 1") }
        verify { viewer.display("ID: 2, Name: Meal 2") }
    }

    @Test
    fun `should ask for meal details after displaying meal list`() {
        // Given
        val validDate = "2023-01-01"
        val parsedDate = LocalDate.parse(validDate)
        val meal = createMeal(submissionDate = parsedDate)

        every { reader.getUserInput() } returnsMany listOf(validDate, "no")
        every { searchMealsByDateUseCase.searchMealsByDate(parsedDate) } returns listOf(meal)

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display("\nWould you like to see details of a specific meal? (Enter meal ID or 'no'):") }
    }

    @Test
    fun `simplified test for meal display`() {
        // Given
        val validDate = "2023-01-01"
        val parsedDate = LocalDate.parse(validDate)
        val meal = createMeal(id = 1234, name = "Test Meal", submissionDate = parsedDate)

        every { reader.getUserInput() } returnsMany listOf(validDate, "1234")
        every { searchMealsByDateUseCase.searchMealsByDate(any()) } returns listOf(meal)

        // When
        searchMealsByDateUI.run()

        // Then
        // تحقق فقط من أن العرض تم استدعاؤه
        verify(atLeast = 3) { viewer.display(any()) }
    }

    @Test
    fun `should display error when invalid meal ID is entered`() {
        // Given
        val validDate = "2023-01-01"
        val parsedDate = LocalDate.parse(validDate)
        val meal = createMeal(id = 1234, submissionDate = parsedDate)
        val invalidMealId = "invalid"

        every { reader.getUserInput() } returnsMany listOf(validDate, invalidMealId)
        every { searchMealsByDateUseCase.searchMealsByDate(parsedDate) } returns listOf(meal)

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display("Invalid meal ID format.") }
    }

    @Test
    fun `should display error when meal ID not found in results`() {
        // Given
        val validDate = "2023-01-01"
        val parsedDate = LocalDate.parse(validDate)
        val meal = createMeal(id = 1234, submissionDate = parsedDate)
        val nonExistentMealId = "5678"

        every { reader.getUserInput() } returnsMany listOf(validDate, nonExistentMealId)
        every { searchMealsByDateUseCase.searchMealsByDate(parsedDate) } returns listOf(meal)

        // When
        searchMealsByDateUI.run()

        // Then
        verify { viewer.display("Meal with ID 5678 not found in the results.") }
    }
}