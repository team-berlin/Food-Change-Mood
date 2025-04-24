package logic.usecase.helper

import com.google.common.truth.Truth.assertThat
import createMeal
import org.berlin.logic.usecase.helper.CantFindWrongIngredientException
import org.berlin.logic.usecase.helper.EmptyIngredientsException
import org.berlin.logic.usecase.helper.EmptyMealsException
import org.berlin.logic.usecase.helper.IngredientGameMealsMapper
import org.berlin.model.Meal
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class IngredientGameMealsMapperTest {
    private val ingredientGameMealsMapper = IngredientGameMealsMapper()


    @Test
    fun `should throw exception when list of meals is empty`() {
        //Given
        val emptyMealsList = emptyList<Meal>()
        //When && Then
        assertThrows<EmptyMealsException> {
            ingredientGameMealsMapper.map(emptyMealsList)
        }

    }

    @Test
    fun `should throw exception when list of meals has meal not have ingredients`() {
        //Given
        val mealsList = LIST_OF_INVALID_MEALS_HAVE_MEAL_NOT_HAVE_INGREDIENT
        //When && Then
        assertThrows<EmptyIngredientsException> {
            ingredientGameMealsMapper.map(mealsList)
        }

    }

    @Test
    fun `should return list of meals after mapped to list for ingredient game exactly same size `() {
        //Given
        val mealsList = LIST_OF_VALID_MEALS
        //When
        val result = ingredientGameMealsMapper.map(mealsList)

        // Then
        assertThat(result).hasSize(3)


    }

    @Test
    fun `should throw exception when try to find wrong ingredients 500 attempts`() {

        //Given list of meals all have same ingredients
        val mealsList = LIST_OF_INVALID_MEALS_ALL_MEALS_HAVE_SAME_INGREDIENT
        //When && Then
        assertThrows<CantFindWrongIngredientException> {
            ingredientGameMealsMapper.map(mealsList)
        }

    }

    @Test
    fun `should throw exception when try to find wrong ingredients 500 attempts only one meal`() {

        //Given list of meals all have same ingredients
        val mealsList = LIST_OF_ONE_MEAL
        //When && Then
        assertThrows<CantFindWrongIngredientException> {
            ingredientGameMealsMapper.map(mealsList)
        }

    }


    companion object{
        val LIST_OF_VALID_MEALS=listOf(
            createMeal(numberOfIngredients = 6, ingredients =  listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeal(numberOfIngredients = 6, ingredients = listOf("abb", "by", "cc", "vd", "wry", "fff")),
            createMeal(numberOfIngredients = 6, ingredients =  listOf("auik", "bgh", "bnmc", "hyud", "hghw", "vbf")),
        )
        val LIST_OF_INVALID_MEALS_HAVE_MEAL_NOT_HAVE_INGREDIENT=listOf(
        createMeal(numberOfIngredients = 6, ingredients = listOf("am", "bn", "ce", "dv", "ww", "dd")),
        createMeal(numberOfIngredients = 6, ingredients = listOf("abb", "by", "cc", "vd", "wry", "fff")),
        createMeal(numberOfIngredients = 6, ingredients = listOf("auik", "bgh", "bnmc", "hyud", "hghw", "vbf")),
        createMeal(numberOfIngredients = 0, ingredients = listOf()),
        )
        val LIST_OF_INVALID_MEALS_ALL_MEALS_HAVE_SAME_INGREDIENT=listOf(
            createMeal(numberOfIngredients = 6, ingredients = listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeal(numberOfIngredients = 6, ingredients = listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeal(numberOfIngredients = 6, ingredients = listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeal(numberOfIngredients = 6, ingredients = listOf("am", "bn", "ce", "dv", "ww", "dd")),
            createMeal(numberOfIngredients = 6, ingredients = listOf("am", "bn", "ce", "dv", "ww", "dd")),
        )
        val LIST_OF_ONE_MEAL=listOf( createMeal(numberOfIngredients = 2, ingredients = listOf("dd","ed")))

    }

}