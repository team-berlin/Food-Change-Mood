package logic.usecase.retrieval

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import org.berlin.logic.repository.MealsRepository
import org.berlin.model.Meal
import org.berlin.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat
import org.berlin.logic.usecase.retrieval.GetQuickHealthyMealsUseCase

class GetQuickHealthyMealsUseCaseTest {

    private lateinit var repository: MealsRepository
    private lateinit var useCase: GetQuickHealthyMealsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk<MealsRepository>()
        useCase = GetQuickHealthyMealsUseCase(repository)
    }

    @Test
    fun `should filter out non-healthy meals`() {
        // Given
        val healthyMeal = createMeal(name = "healthy", minutes = 10, tags = listOf("healthy"))
        val nonHealthyMeal = createMeal(name = "non-healthy", minutes = 10, tags = listOf("junk"))

        every { repository.getAllMeals() } returns listOf(healthyMeal, nonHealthyMeal)

        // When
        val result = useCase.getQuickHealthyMeals()

        // Then
        assertThat(result).contains(healthyMeal)
    }

    @Test
    fun `should filter out meals that take longer than 15 minutes`() {
        // Given
        val quickMeal = createMeal(name = "Quick", minutes = 15, tags = listOf("healthy"))
        val slowMeal = createMeal(name = "Slow", minutes = 16, tags = listOf("healthy"))

        every { repository.getAllMeals() } returns listOf(quickMeal, slowMeal)

        // When
        val result = useCase.getQuickHealthyMeals()

        // Then
        assertThat(result).contains(quickMeal)
    }

    @Test
    fun `should filter out meals with above-threshold fat content`() {
        // Given
        val lowFatMeal = createMeal(
            name = "Low Fat",
            minutes = 10,
            tags = listOf("healthy"),
            nutrition = Nutrition(
                calories = 200.0,
                totalFat = 5.0,
                saturatedFat = 1.0,
                carbohydrates = 15.0,
                sugar = 3.0,
                proteinGrams = 10.0,
                sodium = 200.0
            )
        )

        val highFatMeal = createMeal(
            name = "High Fat",
            minutes = 10,
            tags = listOf("healthy"),
            nutrition = Nutrition(
                calories = 200.0,
                totalFat = 25.0,
                saturatedFat = 10.0,
                carbohydrates = 15.0,
                sugar = 3.0,
                proteinGrams = 10.0,
                sodium = 200.0
            )
        )

        every { repository.getAllMeals() } returns listOf(lowFatMeal, highFatMeal)

        // When
        val result = useCase.getQuickHealthyMeals()

        // Then
        assertThat(result).contains(lowFatMeal)
    }

    @Test
    fun `should sort results by preparation time`() {
        // Given
        val meal1 = createMeal(name = "Fastest", minutes = 5, tags = listOf("healthy"))
        val meal2 = createMeal(name = "Middle", minutes = 10, tags = listOf("healthy"))
        val meal3 = createMeal(name = "Slowest", minutes = 15, tags = listOf("healthy"))

        // Provide meals in random order
        every { repository.getAllMeals() } returns listOf(meal3, meal1, meal2)

        // When
        val result = useCase.getQuickHealthyMeals()

        // Then
        // Check that the list is properly sorted by minutes
        val expectedOrder = listOf(meal1, meal2, meal3)
        assertThat(result).containsExactlyElementsIn(expectedOrder).inOrder()
    }

    @Test
    fun `should return empty list when no meals meet criteria`() {
        // Given
        val nonMatchingMeals = listOf(
            createMeal(name = "Slow Meal", minutes = 30, tags = listOf("dinner")),
            createMeal(name = "Non-Healthy Quick", minutes = 10, tags = listOf("junk"))
        )

        every { repository.getAllMeals() } returns nonMatchingMeals

        // When
        val result = useCase.getQuickHealthyMeals()

        // Then
        assertThat(result).isEmpty()
    }

    @Test
    fun `should return empty list when repository returns empty list`() {
        // Given
        every { repository.getAllMeals() } returns emptyList()

        // When
        val result = useCase.getQuickHealthyMeals()

        // Then
        assertThat(result).isEmpty()
    }

    private fun createMeal(
        name: String,
        minutes: Int = 10,
        tags: List<String> = emptyList(),
        nutrition: Nutrition = Nutrition(
            calories = 200.0,
            totalFat = 10.0,
            saturatedFat = 2.0,
            carbohydrates = 30.0,
            sugar = 5.0,
            proteinGrams = 15.0,
            sodium = 300.0
        )
    ): Meal = Meal(
        name = name,
        id = 1,
        minutes = minutes,
        contributorId = 1,
        submissionDate = LocalDate(2025, 4, 23),
        tags = tags,
        nutrition = nutrition,
        nSteps = 3,
        steps = listOf("Step 1", "Step 2", "Step 3"),
        description = "Test description",
        ingredients = listOf("Ingredient 1", "Ingredient 2"),
        nIngredients = 2
    )
}