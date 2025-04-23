package logic.usecase.suggest

import com.google.common.truth.Truth.assertThat
import createMeal
import io.mockk.every
import io.mockk.mockk
import org.berlin.logic.repository.MealsRepository
import org.berlin.logic.usecase.suggest.SuggestEggFreeSweetUseCase
import org.berlin.model.Meal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class SuggestEggFreeSweetUseCaseTest {

    private lateinit var suggestEggFreeSweetUseCase: SuggestEggFreeSweetUseCase
    private val mealsRepository: MealsRepository = mockk(relaxed = true)


    private val eggFreeSweet1 = createMeal(
        name = "Chocolate Cake",
        id = 1,
        minutes = 60,
        tags = listOf("sweet", "chocolate"),
        ingredients = listOf("flour", "sugar", "cocoa powder", "butter", "milk"),
        steps = listOf("Mix ingredients", "Bake", "Cool", "Frost", "Serve"),
        description = "Rich chocolate cake"
    )

    private val eggFreeSweet2 = createMeal(
        name = "Fruit Salad",
        id = 2,
        minutes = 15,
        tags = listOf("sweet", "fruit", "healthy"),
        ingredients = listOf("apple", "banana", "orange", "berries"),
        steps = listOf("Cut fruits", "Mix and serve"),
        description = "Refreshing fruit salad"
    )

    private val sweetWithEgg = createMeal(
        name = "Custard Tart",
        id = 3,
        minutes = 45,
        tags = listOf("sweet", "tart"),
        ingredients = listOf("flour", "butter", "sugar", "egg", "milk"),
        steps = listOf("Make pastry", "Prepare custard", "Bake", "Cool")
    )

    private val savoryMeal = createMeal(
        name = "Pasta Carbonara",
        id = 4,
        minutes = 30,
        tags = listOf("savory", "pasta"),
        ingredients = listOf("pasta", "egg", "bacon", "cheese"),
        steps = listOf("Cook pasta", "Prepare sauce", "Combine")
    )

    private val meringue = createMeal(
        name = "Meringue",
        id = 5,
        minutes = 50,
        tags = listOf("Sweet"), // Different casing for "sweet"
        ingredients = listOf("egg white", "sugar", "vanilla"),
        steps = listOf("Beat egg whites", "Add sugar", "Bake")
    )

    private val chocolateCake = createMeal(
        name = "Chocolate Cake",
        id = 1,
        minutes = 60,
        tags = listOf("SweEt", "chocolate"),
        ingredients = listOf("flour", "sugar", "cocoa powder", "butter", "milk"),
        steps = listOf("Mix ingredients", "Bake", "Cool", "Frost", "Serve"),
        description = "Rich chocolate cake"
    )


    @BeforeEach
    fun setUp() {
        suggestEggFreeSweetUseCase = SuggestEggFreeSweetUseCase(mealsRepository)
    }

    @Test
    fun `suggestEggFreeSweet should return eggFreeSweet1 when it's the only egg-free sweet`() {

        //. Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggFreeSweet1, sweetWithEgg, savoryMeal
        )

        //. When
        val result = suggestEggFreeSweetUseCase.suggestEggFreeSweet()

        //. Then
        assertThat(result).isEqualTo(eggFreeSweet1)
    }

    @Test
    fun `suggestEggFreeSweet should return eggFreeSweet2 when it's the only egg-free sweet`() {

        //. Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggFreeSweet2, sweetWithEgg, savoryMeal
        )

        //. When
        val result = suggestEggFreeSweetUseCase.suggestEggFreeSweet()

        //. Then
        assertThat(result).isEqualTo(eggFreeSweet2)
    }

    @Test
    fun `suggestEggFreeSweet should return eggFreeSweets`() {

        //. Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggFreeSweet1, sweetWithEgg, savoryMeal, eggFreeSweet2
        )

        //. When
        val suggestions = mutableSetOf<Meal>()
        repeat(2) { // Assuming only 2 egg-free sweets
            suggestions.add(suggestEggFreeSweetUseCase.suggestEggFreeSweet())
        }

        //. Then
        assertThat(suggestions).containsExactly(eggFreeSweet1, eggFreeSweet2)
    }

    @Test
    fun `suggestEggFreeSweet should throw NoSuchElementException when no egg-free sweet found`() {

        //. Given
        every { mealsRepository.getAllMeals() } returns listOf(
            sweetWithEgg, savoryMeal , meringue
        )

        //. Then && When
        assertThrows<NoSuchElementException> {
            suggestEggFreeSweetUseCase.suggestEggFreeSweet()
        }
    }

    @Test
    fun `suggestEggFreeSweet should handle case-insensitive 'sweet' tag and 'egg' ingredient`() {

        //. Given
        every { mealsRepository.getAllMeals() } returns listOf(chocolateCake, meringue)

        //. When
        val result = suggestEggFreeSweetUseCase.suggestEggFreeSweet()

        //. Then
        assertThat(result).isEqualTo(chocolateCake)
    }

}