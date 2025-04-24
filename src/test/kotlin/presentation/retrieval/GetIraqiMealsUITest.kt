package presentation.retrieval

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.berlin.logic.usecase.retrieval.GetIraqiMealsUseCase
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.retrieval.GetIraqiMealsUI
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetIraqiMealsUITest {

    private lateinit var getIraqiMealsUI: GetIraqiMealsUI
    private val getIraqiMealsUseCase : GetIraqiMealsUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)

    @BeforeTest
    fun setUp() {
        getIraqiMealsUI = GetIraqiMealsUI(getIraqiMealsUseCase,viewer)
    }

    private val iraqiMeal1 = createMeal(
        name = "Iraqi Dolma", id = 1, tags = listOf("iraqi", "stuffed"),
        ingredients = listOf("rice", "vegetables", "meat"),
        description = "Traditional Iraqi stuffed vegetables"
    )

    private val iraqiMeal2 = createMeal(
        name = "Kubba", id = 2, tags = listOf("fried"),
        ingredients = listOf("bulgur", "meat"),
        description = "Fried Iraqi dumplings"
    )

    private val iraqiMeal3 = createMeal(
        name = "Kubaa", id = 3, tags = listOf("fried","iraqi"),
        ingredients = listOf("bulgur", "meat"),
        description = null
    )

    @Test
    fun `run should display Iraqi Meals when use case return a list`() {
        // Given
        every { getIraqiMealsUseCase.getIraqiMeals() } returns listOf(iraqiMeal1, iraqiMeal2,iraqiMeal3)
        val expectedDisplayCalls = 1 + (3 * 6) + 1

        // When
        getIraqiMealsUI.run()

        // Then
        verify(exactly = expectedDisplayCalls) { viewer.display(any()) }
    }

    @Test
    fun `run should handle NoSuchElementException and display no Iraqi Meals message`() {
        // Given
        every { getIraqiMealsUseCase.getIraqiMeals() } throws NoSuchElementException("No Iraqi meals found")

        // When
        getIraqiMealsUI.run()

        // Then
        verify(exactly = 1) { viewer.display("No Iraqi meals found.") }
        verify(exactly = 1) { viewer.display(any()) }
    }

}