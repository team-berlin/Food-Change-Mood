package org.berlin.logic.ingredient_game

import org.berlin.model.Meal
import org.berlin.model.MealForIngredientGame

class IngredientGameMealsMapper {
    fun map(meals: List<Meal>): List<MealForIngredientGame> {

        val allMealsIngredients = meals
            .map { meal -> meal.ingredients }
            .flatten()
        return meals
            .map { currentFullMeal ->

                val allMealsIngredientsExceptCurrentMeal = allMealsIngredients
                    .filterNot { ingredient -> ingredient in currentFullMeal.ingredients }
                    .shuffled()

                val correctIngredientIndex = (0..<currentFullMeal.nIngredients).random()

                MealForIngredientGame(
                    mealName = currentFullMeal.name,
                    correctIngredient = currentFullMeal.ingredients[correctIngredientIndex],
                    threeIngredientOnlyOneCorrect =
                        allMealsIngredientsExceptCurrentMeal
                            .take(2)
                            .plus(currentFullMeal.ingredients[correctIngredientIndex])
                            .shuffled(),
                )
            }
    }

}