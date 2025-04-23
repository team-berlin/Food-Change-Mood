package presentation.suggest


import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifySequence
import org.berlin.logic.usecase.suggest.SuggestEggFreeSweetUseCase
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.suggest.SuggestEggFreeSweetUI
import kotlin.test.BeforeTest
import kotlin.test.Test

class SuggestEggFreeSweetUITest {

    private lateinit var suggestEggFreeSweetUI: SuggestEggFreeSweetUI
    private val suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase = mockk(relaxed = true)
    private val viewer: Viewer = mockk(relaxed = true)
    private val reader: Reader = mockk(relaxed = true)


    private val eggFreeSweet1 = createMeal(
        name = "Chocolate Cake", id = 1, minutes = 60, tags = listOf("sweet", "chocolate"),
        ingredients = listOf("flour", "sugar", "cocoa powder", "butter", "milk"),
        steps = listOf("Mix ingredients", "Bake", "Cool", "Frost", "Serve"),
        description = "Rich chocolate cake"
    )

    private val eggFreeSweet2 = createMeal(
        name = "Fruit Salad", id = 2, minutes = 15, tags = listOf("sweet", "fruit", "healthy"),
        ingredients = listOf("apple", "banana", "orange", "berries"),
        steps = listOf("Cut fruits", "Mix and serve"),
        description = "Refreshing fruit salad"
    )

    @BeforeTest
    fun setUp() {
        suggestEggFreeSweetUI = SuggestEggFreeSweetUI(suggestEggFreeSweetUseCase, viewer, reader)
    }

    @Test
    fun `run should display suggested sweet and details when user likes it`() {

        //.Given
        every { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } returns eggFreeSweet1
        every { reader.getUserInput() } returns "yes"

        //. When
        suggestEggFreeSweetUI.run()

        //. Then
        verifySequence {
            viewer.display(any()) // Displaying the header
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display(any()) // Displaying the separator
            viewer.display("Like it? (yes/no/exit)")
            reader.getUserInput()
            viewer.display(any()) // Displaying "--- Sweet Details ---"
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display("Ingredients: ${eggFreeSweet1.ingredients.joinToString(", ")}")
            viewer.display("Steps:")
            eggFreeSweet1.steps.forEachIndexed { index, step ->
                viewer.display("${index + 1}. $step")
            }
        }
    }

    @Test
    fun `run should display another option when user does not like it`() {

        //. Given
        every { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } returns eggFreeSweet1 andThen eggFreeSweet2
        every { reader.getUserInput() } returns "no" andThen "yes"

        //. When
        suggestEggFreeSweetUI.run()

        verifySequence {
            viewer.display(any()) // Displaying the header
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display(any()) // Displaying the separator
            viewer.display("Like it? (yes/no/exit)")
            reader.getUserInput()
            viewer.display("Disliked. Getting another suggestion.")
            viewer.display(any()) // Displaying the header
            viewer.display("Name: ${eggFreeSweet2.name}")
            viewer.display("Description: ${eggFreeSweet2.description}")
            viewer.display(any()) // Displaying the separator
            viewer.display("Like it? (yes/no/exit)")
            reader.getUserInput()
            viewer.display(any()) // Displaying "--- Sweet Details ---"
            viewer.display("Name: ${eggFreeSweet2.name}")
            viewer.display("Description: ${eggFreeSweet2.description}")
            viewer.display("Ingredients: ${eggFreeSweet2.ingredients.joinToString(", ")}")
            viewer.display("Steps:")
            eggFreeSweet2.steps.forEachIndexed { index, step ->
                viewer.display("${index + 1}. $step")
            }
        }
    }

    @Test
    fun `run should display suggested sweet and exit when user enters exit`() {
        //. Given
        every { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } returns eggFreeSweet1
        every { reader.getUserInput() } returns "exit"

        //. When
        suggestEggFreeSweetUI.run()

        // Then
        verifySequence {
            viewer.display(any()) // Header
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display(any()) // Separator
            viewer.display("Like it? (yes/no/exit)")
            reader.getUserInput() // "exit"
        }
        verify(exactly = 1) { suggestEggFreeSweetUseCase.suggestEggFreeSweet() }
    }

    @Test
    fun `run should display No more egg-free sweets to suggest when meals list is empty`() {

        //. Given
        every { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } throws NoSuchElementException()

        //. When
        suggestEggFreeSweetUI.run()

        //. Then
        verify { viewer.display("No more egg-free sweets to suggest") } // This should now use MockK's verify
        verify(exactly = 1) { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } // And this one too
    }

    @Test
    fun `run should display a meal then no more suggestions after dislike`() {

        //. Given
        every { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } returns eggFreeSweet1 andThenThrows NoSuchElementException()
        every { reader.getUserInput() } returns "no"

        //. When
        suggestEggFreeSweetUI.run()

        verifySequence {
            viewer.display(any()) // Header
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display(any()) // Separator
            viewer.display("Like it? (yes/no/exit)")
            reader.getUserInput() // "no"
            viewer.display("Disliked. Getting another suggestion.")
            viewer.display("No more egg-free sweets to suggest")
        }
    }

    @Test
    fun `run should handle invalid input and re-prompt user`() {
        // Given
        every { suggestEggFreeSweetUseCase.suggestEggFreeSweet() } returns eggFreeSweet1
        every { reader.getUserInput() } returnsMany listOf("oops", "yes") // Invalid input then "yes"

        // When
        suggestEggFreeSweetUI.run()

        // Then
        verifySequence {
            viewer.display(any()) // Header
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display(any()) // Separator
            viewer.display("Like it? (yes/no/exit)")
            reader.getUserInput() // "oops"
            viewer.display("Invalid Input")
            viewer.display("Like it? (yes/no/exit)") // Re-prompt
            reader.getUserInput() // "yes"
            viewer.display(any()) // Sweet Details header
            viewer.display("Name: ${eggFreeSweet1.name}")
            viewer.display("Description: ${eggFreeSweet1.description}")
            viewer.display("Ingredients: ${eggFreeSweet1.ingredients.joinToString(", ")}")
            viewer.display("Steps:")
            eggFreeSweet1.steps.forEachIndexed { index, step ->
                viewer.display("${index + 1}. $step")
            }
        }
    }
}