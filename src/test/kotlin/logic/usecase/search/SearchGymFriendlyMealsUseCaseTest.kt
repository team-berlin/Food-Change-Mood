package logic.usecase.search

import com.google.common.truth.Truth.assertThat
import createMeal
import fake.createNutrition
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.search.SearchGymFriendlyMealsUseCase
import org.berlin.model.CaloriesAndProteinTolerance
import org.berlin.model.GymHelperInput
import org.berlin.model.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SearchGymFriendlyMealsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var gymHelper: SearchGymFriendlyMealsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk<MealsRepository>(relaxed = true)
        gymHelper = SearchGymFriendlyMealsUseCase(mealsRepository)
    }

    @ParameterizedTest
    @CsvSource(
        "900.0, 120.0",
        "500.1, 23.2"
    )
    fun `should return the valid meal when give it valid values fo calories and protein`(
        calories: Double,
        protein: Double
    ) {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(nutrition = createNutrition(calories = 1000.0 , proteinGrams = 200.0)),
            createMeal(nutrition = createNutrition(calories = 500.1 , proteinGrams = 23.2)),
            createMeal(nutrition = createNutrition(calories = 300.5 , proteinGrams = 70.0)),
            createMeal(nutrition = createNutrition(calories = 900.0 , proteinGrams = 120.0)),
            createMeal(nutrition = createNutrition(calories = 1200.0 , proteinGrams = 150.0)),
        )
        val tolerance = CaloriesAndProteinTolerance(0, 0)

        // When
        val result = gymHelper.getMealsByCaloriesAndProtein(
            GymHelperInput(
                calories,
                protein,
                tolerance
            )
        )

        // Then
        assertThat(result).isEqualTo(listOf(createMeal(nutrition = createNutrition(calories = calories , proteinGrams = protein))))
    }

    @Test
    fun `should return the valid meals when give it valid values fo calories and protein in range of tolerance`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(nutrition = createNutrition(calories = 1000.0 , proteinGrams = 200.0)),
            createMeal(nutrition = createNutrition(calories = 500.1 , proteinGrams = 23.2)),
            createMeal(nutrition = createNutrition(calories = 300.5 , proteinGrams = 70.0)),
            createMeal(nutrition = createNutrition(calories = 900.0 , proteinGrams = 120.0)),
            createMeal(nutrition = createNutrition(calories = 1200.0 , proteinGrams = 150.0)),
        )
        val calories = 910.0
        val protein = 100.0
        val tolerance = CaloriesAndProteinTolerance(20, 20)

        // When
        val result = gymHelper.getMealsByCaloriesAndProtein(
            GymHelperInput(
                calories,
                protein,
                tolerance
            )
        )

        // Then
        assertThat(result).isEqualTo(listOf(createMeal(nutrition = createNutrition(calories = 900.0 , proteinGrams = 120.0))))
    }

    @Test
    fun `should throw exception when there is no meals matching calories and protein values`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(nutrition = createNutrition(calories = 1000.0 , proteinGrams = 200.0)),
            createMeal(nutrition = createNutrition(calories = 500.1 , proteinGrams = 23.2)),
            createMeal(nutrition = createNutrition(calories = 300.5 , proteinGrams = 70.0)),
            createMeal(nutrition = createNutrition(calories = 900.0 , proteinGrams = 120.0)),
            createMeal(nutrition = createNutrition(calories = 1200.0 , proteinGrams = 150.0)),
        )
        val calories = 99999.0
        val protein = 9999.0
        val tolerance = CaloriesAndProteinTolerance(0, 0)

        // When & Then
        assertThrows<NoSuchElementException> {
            gymHelper.getMealsByCaloriesAndProtein(
                GymHelperInput(
                    calories,
                    protein,
                    tolerance
                )
            )
        }
    }

}