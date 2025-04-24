package presentation.suggest

import com.google.common.truth.Truth.assertThat
import fixture.suggest.TestKetoMeals.ketoMealProteinZero
import fixture.suggest.TestKetoMeals.ketoMealValid
import io.mockk.*
import org.berlin.logic.usecase.suggest.SuggestKetoMealUseCase
import org.berlin.presentation.common.showMealDetails
import org.berlin.presentation.input_output.Reader
import org.berlin.presentation.input_output.Viewer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SuggestKetoMealUITest {

    private lateinit var suggestKetoMealUseCase: SuggestKetoMealUseCase
    private lateinit var viewer: Viewer
    private lateinit var reader: Reader
    private lateinit var suggestKetoMealUI: SuggestKetoMealUI

    @BeforeEach
    fun setup() {
        suggestKetoMealUseCase = mockk(relaxed = true)
        viewer = mockk(relaxed = true)
        reader = mockk(relaxed = true)
        suggestKetoMealUI = SuggestKetoMealUI(suggestKetoMealUseCase, viewer, reader)

        mockkStatic(::showMealDetails)
        every { showMealDetails(any(), any()) } returns Unit
    }

    @Test
    fun `run displays goodbye when no meals`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns emptyList()
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen).containsExactly("No keto friendly meals available. Goodbye!")
    }

    @Test
    fun `run shows details and stops on yes`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns listOf(ketoMealValid, ketoMealProteinZero)
        every { reader.getUserInput() } returns "y"
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen[0]).contains("--- Keto Meal Suggestion ---")
        assertThat(seen[1]).contains("Name: ${ketoMealValid.name}")
        assertThat(seen[4]).contains("Like it?")
        verify(exactly = 1) { showMealDetails(ketoMealValid, viewer) }
    }

    @Test
    fun `run moves to next suggestion on 'n' input`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns listOf(ketoMealValid, ketoMealProteinZero)
        every { reader.getUserInput() } returnsMany listOf("n")
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen).contains("Alright, let's try another meal.")
        assertThat(seen).contains("Name: ${ketoMealProteinZero.name}")
    }

    @Test
    fun `run exits immediately on 'e' input`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns listOf(ketoMealValid, ketoMealProteinZero)
        every { reader.getUserInput() } returns "e"
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen[1]).isEqualTo("Name: ${ketoMealValid.name}")
        assertThat(seen).doesNotContain("Alright, let's try another meal.")
        verify(exactly = 0) { showMealDetails(any(), any()) }
    }


    @Test
    fun `run exhausts all suggestions then says no more meals`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns listOf(ketoMealValid, ketoMealProteinZero)
        every { reader.getUserInput() } returnsMany listOf("n", "n")
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen).contains("No more keto friendly meals available. Goodbye!")
    }

    @Test
    fun `run treats null input as invalid then exits`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns listOf(ketoMealValid)
        every { reader.getUserInput() } returnsMany listOf(null, "e")
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen).contains("Invalid input, please try again...")
    }

    @Test
    fun `run treats unrecognized input as invalid then exits`() {
        // given
        every { suggestKetoMealUseCase.suggestKetoMeal() } returns listOf(ketoMealValid)
        every { reader.getUserInput() } returnsMany listOf("foo", "e")
        val seen = mutableListOf<String>()
        every { viewer.display(capture(seen)) } returns Unit

        // when
        suggestKetoMealUI.run()

        // then
        assertThat(seen).contains("Invalid input, please try again...")
    }
}
