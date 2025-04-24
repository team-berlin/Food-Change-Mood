package org.berlin.logic.usecase.helper

import org.berlin.model.Meal
import org.berlin.model.MealForIngredientGame

class IngredientGameMealsMapper {

    fun map(meals: List<Meal>): List<MealForIngredientGame> {

        validateEmptyMeals(meals)

        val allMealsIngredients = getAllMealsIngredients(meals)
        return meals.map { currentFullMeal ->
            validateEmptyIngredients(currentFullMeal)
            val correctIngredient = getRandomIngredient(currentFullMeal)
            val wrongIngredients = getTwoWrongIngredient(allMealsIngredients, currentFullMeal)
            MealForIngredientGame(
                mealName = currentFullMeal.name,
                correctIngredient = correctIngredient,
                threeIngredientOnlyOneCorrect = (wrongIngredients + correctIngredient).shuffled()
            )
        }
    }

    private fun validateEmptyMeals(meals: List<Meal>) {
        if (meals.isEmpty()) throw Exception("list of meals is empty")
    }

    private fun getAllMealsIngredients(meals: List<Meal>): List<String> {
        validateEmptyMeals(meals)
        return meals.flatMap { meal ->
            validateEmptyIngredients(meal)
            meal.ingredients
        }.toSet().toList()
    }

    private fun validateEmptyIngredients(meal: Meal) {
        if (meal.numberOfIngredients < 1) throw Exception("there is invalid meal ingredients")
    }

    private fun getRandomIngredient(meal: Meal): String {
        validateEmptyIngredients(meal)
        return meal.ingredients.random()
    }

    private fun getTwoWrongIngredient(
        allMealsIngredients: List<String>, meal: Meal,
    ): List<String> {
        validateEmptyIngredients(meal)

        val wrongIngredients = mutableSetOf<String>()
        var counter = 0
        while (wrongIngredients.size < 2 && counter < 500) {
            val randomIngredient = allMealsIngredients.random()
            if (randomIngredient !in meal.ingredients) {
                wrongIngredients.add(randomIngredient)
            }
            counter++
        }
        if (wrongIngredients.size < 2) throw Exception("failed to find two wrong ingredients after 500 attempts")

        return wrongIngredients.toList()

    }

}