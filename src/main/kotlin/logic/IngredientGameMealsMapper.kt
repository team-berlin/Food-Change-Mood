package org.berlin.logic

import org.berlin.model.Meal
import org.berlin.model.MealForIngredientGame

class IngredientGameMealsMapper {
    fun map(meals: List<Meal>): List<MealForIngredientGame> {

        if (meals.isEmpty())
            throw Exception("list of meals is empty")

        val allMealsIngredients = meals
            .flatMap { meal -> meal.ingredients }
            .toSet()
            .toList()

        return meals.map { currentFullMeal ->
            if (currentFullMeal.nIngredients < 1)
                throw Exception("there is invalid meal ingredient")
            val correctIngredientIndex = (0 until currentFullMeal.nIngredients).random()
            val correctIngredient = currentFullMeal.ingredients[correctIngredientIndex]
            val currentIngredientSet = currentFullMeal.ingredients.toSet()


            val wrongIngredients = mutableSetOf<String>()
            while (wrongIngredients.size < 2) {
                val randomIngredient = allMealsIngredients.random()
                if (randomIngredient !in currentIngredientSet) {
                    wrongIngredients.add(randomIngredient)
                }
            }

            val options = (wrongIngredients + correctIngredient).shuffled()

            MealForIngredientGame(
                mealName = currentFullMeal.name,
                correctIngredient = correctIngredient,
                threeIngredientOnlyOneCorrect = options
            )
        }
    }
}