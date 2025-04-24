package logic.usecase.search

import com.google.common.truth.Truth.assertThat
import common.generateFoodCulture
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.search.SearchFoodByCultureUseCase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource


class SearchFoodByCultureUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var exploreFoodUseCase: SearchFoodByCultureUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk<MealsRepository>()
        exploreFoodUseCase = SearchFoodByCultureUseCase(mealsRepository)
    }

    @Test
    fun `should return meal when country matches the name`() {
        // Given
        val mealWithName = createMeal(
            name = "Italian Pasta",
            tags = listOf("tag1"),
            description = "Nice food",
            steps = listOf("Boil")
        )
        every { mealsRepository.getAllMeals() } returns listOf(mealWithName)
        val searchInput = "italian"
        // When
        val result = exploreFoodUseCase.exploreFoodByCountry(searchInput)

        // Then
        assertThat(result).containsExactly(mealWithName)
    }

    @Test
    fun `should return meal when country matches the tags`() {
        //given
        val mealWithTags = createMeal(
            name = "Meal",
            tags = listOf("Indian", "Spicy"),
            description = "Tasty",
            steps = listOf("Cook")
        )
        every { mealsRepository.getAllMeals() } returns listOf(mealWithTags)
        val searchInput = "indian"

        //when
        val result = exploreFoodUseCase.exploreFoodByCountry(searchInput)

        //then
        assertThat(result).containsExactly(mealWithTags)
    }

    @Test
    fun `should return meal when country matches the description`() {
        //given
        val mealWithDescription = createMeal(
            name = "Something",
            tags = listOf("tag1"),
            description = "Famous Egyptian dish",
            steps = listOf("Fry")
        )
        every { mealsRepository.getAllMeals() } returns listOf(mealWithDescription)
        val searchInput = "Egyptian"

        //when
        val result = exploreFoodUseCase.exploreFoodByCountry(searchInput)

        //then
        assertThat(result).containsExactly(mealWithDescription)
    }

    @Test
    fun `should return meal when country matches the steps`() {
        //given
        val mealWithSteps = createMeal(
            name = "Dish",
            tags = listOf("tag1"),
            description = "Yummy",
            steps = listOf("Add italian spices", "Bake")
        )
        every { mealsRepository.getAllMeals() } returns listOf(mealWithSteps)
        val searchInput = "italian"

        //when
        val result = exploreFoodUseCase.exploreFoodByCountry(searchInput)

        //then
        assertThat(result).containsExactly(mealWithSteps)
    }


    @Test
    fun `should return meal when matching country in any field`() {
        //given
        val foodCulture = createMeal(
            name = "French Toast",
            tags = listOf("France"),
            description = "Classic French dish",
            steps = listOf("Bake")
        )
        every { mealsRepository.getAllMeals() } returns listOf(foodCulture)
        val searchInput = "french"

        //when
        val result = exploreFoodUseCase.exploreFoodByCountry(searchInput)

        //then
        assertThat(result).containsExactly(foodCulture)
    }

    @Test
    fun `should throw exception when search for not available county`() {
        //given
        every { mealsRepository.getAllMeals() } returns emptyList()

        val searchInput = "jsf"

        //when&Then
        assertThrows<NoSuchElementException> {
            exploreFoodUseCase.exploreFoodByCountry(searchInput)
        }
    }

    @Test
    fun ` should return all list when is less than random number`() {
        every { mealsRepository.getAllMeals() } returns generateFoodCulture(10)

        val result = exploreFoodUseCase.exploreFoodByCountry("")

        assertEquals(10, result.size)

    }

    @Test
    fun ` should return all list when filter list is equal random number`() {
        every { mealsRepository.getAllMeals() } returns generateFoodCulture(20)

        val result = exploreFoodUseCase.exploreFoodByCountry("")

        assertEquals(20, result.size)

    }

    @Test
    fun ` should return just 20 random meals when list more than random number`() {
        every { mealsRepository.getAllMeals() } returns generateFoodCulture(30)

        val result = exploreFoodUseCase.exploreFoodByCountry("")

        assertEquals(20, result.size)

    }

    @ParameterizedTest
    @ValueSource(strings = ["French", "french", "FRENCH", "FrEnCh"])
    fun `should find correct result in case-insensitive way`(searchInput: String) {

        val foodCulture = createMeal(
            name = "French Toast",
            tags = listOf("France"),
            description = "Classic French dish",
            steps = listOf("Bake")
        )
        every { mealsRepository.getAllMeals() } returns listOf(foodCulture)

        //when
        val result = exploreFoodUseCase.exploreFoodByCountry(searchInput)

        //Then
        assertThat(result).containsExactly(foodCulture)

    }

}

