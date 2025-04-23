package logic.usecase.helper

import com.google.common.truth.Truth.assertThat
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.berlin.logic.usecase.helper.IngredientGameMealsMapper
import org.berlin.model.Meal
import org.berlin.model.Nutrition
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class IngredientGameMealsMapperTest {
    private val ingredientGameMealsMapper = IngredientGameMealsMapper()


    @Test
    fun `should throw exception when list of meals is empty`() {
        //Given
        val emptyMealsList = emptyList<Meal>()
        //When && Then
        assertThrows<Exception> {
            ingredientGameMealsMapper.map(emptyMealsList)
        }

    }

    @Test
    fun `should throw exception when list of meals has meal not have ingredients`() {
        //Given list of meals have meal not have ingredients
        val mealsList = listOf(
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeals(6, listOf("abb", "by", "cc", "vd", "wry", "fff")),
            createMeals(6, listOf("auik", "bgh", "bnmc", "hyud", "hghw", "vbf")),
            createMeals(0, listOf()),
        )
        //When && Then
        assertThrows<Exception> {
            ingredientGameMealsMapper.map(mealsList)
        }

    }

    @Test
    fun `should return list of meals after mapped to list for ingredient game exactly same size `() {
        //Given list of correct meals
        val mealsList = listOf(
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeals(6, listOf("abb", "by", "cc", "vd", "wry", "fff")),
            createMeals(6, listOf("auik", "bgh", "bnmc", "hyud", "hghw", "vbf")),
        )
        //When
        val result = ingredientGameMealsMapper.map(mealsList)

        // Then
        assertThat(result).hasSize(3)


    }

    @Test
    fun `should throw exception when try to find wrong ingredients 500 attempts`() {

        //Given list of meals all have same ingredients
        val mealsList = listOf(
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeals(6, listOf("am", "bn", "ce", "dv", "ww", "dd")),
        )
        //When && Then
        assertThrows<Exception> {
            ingredientGameMealsMapper.map(mealsList)
        }

    }


    private fun createMeals(nIngredients: Int, ingredients: List<String>) = Meal(
        name = "name",
        id = 0,
        minutes = 1,
        contributorId = 123,
        submissionDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
        tags = listOf("tag1", "tag2"),
        nutrition = Nutrition(
            calories = 100.0,
            totalFat = 10.0,
            sugar = 10.0,
            sodium = 10.0,
            proteinGrams = 10.0,
            saturatedFat = 10.0,
            carbohydrates = 10.0
        ),
        nSteps = 1,
        steps = listOf("step1", "step2"),
        description = "description",
        ingredients = ingredients,
        nIngredients = nIngredients

    )

}