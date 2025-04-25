package logic.usecase.retrieval

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.retrieval.GetIraqiMealsUseCase
import org.junit.jupiter.api.assertThrows
import kotlin.test.BeforeTest
import kotlin.test.Test

class GetIraqiMealsUseCaseTest {

    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase
    private var mealsRepository: MealsRepository = mockk(relaxed = true)

    private val iraqiMeal1 = createMeal(
        name = "Iraqi Dolma", id = 1, tags = listOf("iraqi", "stuffed"),
        ingredients = listOf("rice", "vegetables", "meat"),
        description = "Traditional Iraqi stuffed vegetables"
    )

    private val iraqiMeal2 = createMeal(name = "Kubba", id = 2, tags = listOf("fried"),
        ingredients = listOf("bulgur", "meat"),
        description = "Fried Iraqi dumplings"
    )

    private val nonIraqiMeal1 = createMeal(
        name = "Pasta Carbonara", id = 3, tags = listOf("italian", "pasta"),
        ingredients = listOf("pasta", "eggs", "bacon")
    )

    private val nonIraqiMeal2 = createMeal(
        name = "Sushi", id = 4, tags = listOf("japanese", "fish"),
        ingredients = listOf("rice", "seaweed", "fish"),
        description = "Japanese raw fish dish"
    )

    private val iraqiMealWithTagAndDescription = createMeal(
        name = "Another Iraqi Dish", id = 5, tags = listOf("middle-eastern", "iraqi-style"),
        ingredients = listOf("lamb", "spices"),
        description = "A delicious Iraqi recipe"
    )

    @BeforeTest
    fun setUp() {
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepository)
    }

    @Test
    fun `getIraqiMeals should return the expected meals when found by tag`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(iraqiMeal1 , nonIraqiMeal1, nonIraqiMeal2)

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(result).containsExactly(iraqiMeal1)
    }

    @Test
    fun `getIraqiMeals should return the expected meals when found by description`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(iraqiMeal2 , nonIraqiMeal1, nonIraqiMeal2)

        // When
        val result = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(result).containsExactly(iraqiMeal2)
    }

    @Test
    fun `getIraqiMeals should return a list of all Iraqi meals when multiple are present`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(iraqiMeal1, nonIraqiMeal1, iraqiMeal2)

        // When
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(iraqiMeals).containsExactly(iraqiMeal1, iraqiMeal2)
    }

    @Test
    fun `getIraqiMeals should return a list of Iraqi meals found by both tag and description variations`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(iraqiMeal1, iraqiMealWithTagAndDescription, nonIraqiMeal1)

        // When
        val iraqiMeals = getIraqiMealsUseCase.getIraqiMeals()

        // Then
        assertThat(iraqiMeals).containsExactly(iraqiMeal1, iraqiMealWithTagAndDescription)
    }

    @Test
    fun `getIraqiMeals should throw NoSuchElementException when no Iraqi meals are found`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(nonIraqiMeal1,nonIraqiMeal2)

        // When && Then
        assertThrows<NoSuchElementException> {
            getIraqiMealsUseCase.getIraqiMeals()
        }
    }

    @Test
    fun `getIraqiMeals should throw NoSuchElementException when no meals are found`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When && Then
        assertThrows<NoSuchElementException> {
            getIraqiMealsUseCase.getIraqiMeals()
        }
    }

}