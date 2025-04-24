package presentation.suggest

import createMeal
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.suggest.SuggestItalianFoodForLargeGroupUseCase
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.suggest.SuggestItalianFoodForLargeGroupUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestItalianFoodForLargeGroupUITest {

    private lateinit var suggestItalianFoodForLargeGroupUseCase: SuggestItalianFoodForLargeGroupUseCase
    private lateinit var viewer: Viewer
    private lateinit var ui: SuggestItalianFoodForLargeGroupUI

    @BeforeEach
    fun setUp() {
        suggestItalianFoodForLargeGroupUseCase = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        ui = SuggestItalianFoodForLargeGroupUI(suggestItalianFoodForLargeGroupUseCase, viewer)
    }

    @Test
    fun `should display meals returned by use case`() {
        // Given
        val meals = listOf(
            createMeal(
                name = "Italian Pasta", tags = listOf("italian", "for-large-groups"),
            ),
            createMeal(
                name = "Italian Pizza", tags = listOf("italian", "for-large-groups"),
            )
        )
        every { suggestItalianFoodForLargeGroupUseCase.suggestItalianMealsForLargeGroup() } returns meals

        // When
        ui.run()

        // Then
        meals.forEach { meal ->
            verify(exactly = 1) { viewer.display("- Meal Name: ${meal.name}\n") }
        }
    }

}
