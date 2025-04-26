package presentation.suggest

import common.genarateEasyMeals
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.suggest.SuggestEasyFoodUseCase
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.suggest.SuggestionEasyFoodUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestionEasyFoodUITest {
    private lateinit var suggestEasyFoodUseCase: SuggestEasyFoodUseCase
    private lateinit var suggestEasyFood: SuggestionEasyFoodUI
    private lateinit var viewer: Viewer

    @BeforeEach
    fun setup() {
        viewer = mockk<Viewer>(relaxed = true)
        suggestEasyFoodUseCase = mockk()
        suggestEasyFood = SuggestionEasyFoodUI(suggestEasyFoodUseCase, viewer)

    }

    @Test
    fun `run should display easy meal list`() {
        val meals = genarateEasyMeals(10)
        every { suggestEasyFoodUseCase.getEasyFoodSuggestion() } returns meals

        suggestEasyFood.run()

        verify { viewer.display(any()) }
    }
}
