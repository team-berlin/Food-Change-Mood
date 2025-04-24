package presentation.retrieval

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.berlin.logic.usecase.retrieval.GetIraqiMealsUseCase
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.retrieval.GetIraqiMealsUI
import org.junit.jupiter.api.assertThrows
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

    @Test
    fun `run should display Iraqi Meals when use case return a list`() {
        //. Given
        every { getIraqiMealsUseCase.getIraqiMeals() } returns listOf(iraqiMeal1, iraqiMeal2)

        //. When
        getIraqiMealsUI.run()

        //. Then
        verifySequence {
            viewer.display("\n--- Iraqi Meals ---")

            // Details for iraqiMeal1
            viewer.display("Name: ${iraqiMeal1.name}")
            viewer.display("ID: ${iraqiMeal1.id}")
            viewer.display("Description: ${iraqiMeal1.description}")
            viewer.display("Tags: ${iraqiMeal1.tags.joinToString(", ")}")
            viewer.display("Ingredients: ${iraqiMeal1.ingredients.joinToString(", ")}")
            viewer.display("---")

            // Details for iraqiMeal2
            viewer.display("Name: ${iraqiMeal2.name}")
            viewer.display("ID: ${iraqiMeal2.id}")
            viewer.display("Description: ${iraqiMeal2.description}")
            viewer.display("Tags: ${iraqiMeal2.tags.joinToString(", ")}")
            viewer.display("Ingredients: ${iraqiMeal2.ingredients.joinToString(", ")}")
            viewer.display("---")


            viewer.display("--- End of Iraqi Meals ---")
        }
    }

    @Test
    fun `run should handle NoSuchElementException and display no Iraqi Meals message`() {
        //. Given
        every { getIraqiMealsUseCase.getIraqiMeals() } throws NoSuchElementException("No Iraqi meals found")

        //. When
        getIraqiMealsUI.run()

        //. Then
        verify { viewer.display("No Iraqi meals found.") }
    }

}