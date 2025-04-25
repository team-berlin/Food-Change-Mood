package presentation.retrieval

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.retrieval.GetSeafoodMealsUseCase
import org.berlin.model.SeafoodMeal
import org.berlin.presentation.input_output.Viewer
import org.berlin.presentation.retrieval.GetSeafoodMealsUI
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetSeafoodMealsUITest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var seafoodMealsUseCase: GetSeafoodMealsUseCase
    private lateinit var seafoodMealsUI: GetSeafoodMealsUI
    private val viewer: Viewer = mockk(relaxed = true)

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk()
        seafoodMealsUseCase = mockk()
        seafoodMealsUI = GetSeafoodMealsUI(seafoodMealsUseCase, viewer)
    }

    @Test
    fun `should display all seafood meals when UI starts`() {
        //Given
        val seafoodMeal = SeafoodMeal(
            name = "not your mamma s tuna salad",
            protein = 35.0
        )
        every { seafoodMealsUseCase.getSeafoodMeals() } returns listOf(seafoodMeal)
        //When
        seafoodMealsUI.run()
        //Then
        verify { viewer.display("1 ,Name:not your mamma s tuna salad, has protein value:35.0") }
    }
}