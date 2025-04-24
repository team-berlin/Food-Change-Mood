package presentation.retrieval

import common.generatePotatoMeals
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.usecase.retrieval.GetMealsContainsPotatoUseCase
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.retrieval.GetMealsContainsPotatoUI
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class GetMealsContainsPotatoUITest {

    private lateinit var viewer: Viewer
    private lateinit var getMealsContainsPotatoUseCase: GetMealsContainsPotatoUseCase
    private lateinit var getMealsContainsPotatoUI: GetMealsContainsPotatoUI

    @BeforeEach
    fun setup() {
        viewer = mockk<Viewer>(relaxed = true)
        getMealsContainsPotatoUseCase = mockk()
        getMealsContainsPotatoUI = GetMealsContainsPotatoUI(getMealsContainsPotatoUseCase, viewer)
    }

    @Test
    fun `Should display random list of items that contains potatoes`() {
        // Given
        val meals = generatePotatoMeals(10)
        every { getMealsContainsPotatoUseCase.getMealsContainsPotato() } returns listOf(meals.toString())

        // When
        getMealsContainsPotatoUI.run()

        // Then
        verify { viewer.display(any()) }
    }

}
