package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.suggest.SuggestItalianFoodForLargeGroupUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestItalianFoodForLargeGroupUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var useCase: SuggestItalianFoodForLargeGroupUseCase

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk(relaxed = true)
        useCase = SuggestItalianFoodForLargeGroupUseCase(mealsRepository)
    }

    @Test
    fun `suggestItalianMealsForLargeGroup should return a list of Italian meals for large groups`() {
        // Given
        val italianMeal = createMeal(
            tags = listOf("italian", "for-large-groups")
        )
        every { mealsRepository.getAllMeals() } returns listOf(italianMeal)

        // When
        val result = useCase.suggestItalianMealsForLargeGroup()

        // Then
        assertThat(result).containsExactly(italianMeal)
    }

    @Test
    fun `suggestItalianMealsForLargeGroup should throw an exception when no Italian meals for large groups`() {
        // Given
        val nonItalianMeal = createMeal(
            tags = listOf("saudi", "for-large-groups")
        )
        every { mealsRepository.getAllMeals() } returns listOf(nonItalianMeal)

        // When & Then
        assertThrows<NoSuchElementException> {
            useCase.suggestItalianMealsForLargeGroup()
        }
    }

    @Test
    fun `suggestItalianMealsForLargeGroup should throw an exception when Italian food is not for large groups`() {
        // Given
        val nonLargeGroupMeal = createMeal(
            tags = listOf("italian", "for-small-groups")
        )
        every { mealsRepository.getAllMeals() } returns listOf(nonLargeGroupMeal)

        // When & Then
        assertThrows<NoSuchElementException> {
            useCase.suggestItalianMealsForLargeGroup()
        }
    }

    @Test
    fun `suggestItalianMealsForLargeGroup should handle tags with whitespace`() {
        // Given
        val italianMeal = createMeal(
            tags = listOf("italian", "  for-large-groups  ")
        )
        every { mealsRepository.getAllMeals() } returns listOf(italianMeal)

        // When
        val result = useCase.suggestItalianMealsForLargeGroup()

        // Then
        assertThat(result).containsExactly(italianMeal)
    }
}
