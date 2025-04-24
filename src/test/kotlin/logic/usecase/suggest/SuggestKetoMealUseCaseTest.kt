package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import fixture.suggest.TestKetoMeals.ketoMealValid
import fixture.suggest.TestKetoMeals.ketoMealProteinZero
import fixture.suggest.TestKetoMeals.nonKetoHighCarb
import fixture.suggest.TestKetoMeals.nonKetoLowRatio
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.suggest.SuggestKetoMealUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestKetoMealUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var useCase: SuggestKetoMealUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        useCase = SuggestKetoMealUseCase(mealsRepository)
    }

    @Test
    fun `should throw when repository returns empty list`() {
        // given
        every { mealsRepository.getAllMeals() } returns emptyList()
        // when
        val ex = assertThrows<NoSuchElementException> {
            useCase.suggestKetoMeal()
        }
        // then
        assertThat(ex).hasMessageThat().isEqualTo(
            "No keto friendly meals found"
        )
    }

    @Test
    fun `should throw when no keto-friendly meals present`() {
        // given
        every { mealsRepository.getAllMeals() } returns listOf(
            nonKetoHighCarb,
            nonKetoLowRatio
        )
        // when
        val ex = assertThrows<NoSuchElementException> {
            useCase.suggestKetoMeal()
        }
        // then
        assertThat(ex).hasMessageThat().isEqualTo(
            "No keto friendly meals found"
        )
    }

    @Test
    fun `should return only keto-friendly meals when mixed`() {
        // given
        every { mealsRepository.getAllMeals() } returns listOf(
            nonKetoHighCarb,
            ketoMealValid,
            nonKetoLowRatio,
            ketoMealProteinZero
        )
        // when
        val result = useCase.suggestKetoMeal()
        // then
        assertThat(result).containsExactly(
            ketoMealValid,
            ketoMealProteinZero
        )
    }

    @Test
    fun `protein-zero meals are considered keto-friendly`() {
        // given
        every { mealsRepository.getAllMeals() } returns listOf(
            ketoMealProteinZero,
            nonKetoHighCarb
        )
        // when
        val result = useCase.suggestKetoMeal()
        // then
        assertThat(result).containsExactly(ketoMealProteinZero)
    }

    @Test
    fun `meals failing fat-protein ratio are excluded`() {
        // given
        every { mealsRepository.getAllMeals() } returns listOf(
            nonKetoLowRatio,
            ketoMealValid
        )
        // when
        val result = useCase.suggestKetoMeal()
        // then
        assertThat(result).containsExactly(ketoMealValid)
    }

    @Test
    fun `repository getAllMeals is always called exactly once`() {
        // given
        every { mealsRepository.getAllMeals() } returns listOf(ketoMealValid)
        // when
        useCase.suggestKetoMeal()
        // then
        verify(exactly = 1) { mealsRepository.getAllMeals() }
    }

}
