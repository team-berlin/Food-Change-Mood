package org.berlin.logic

import org.berlin.model.MealForIngredientGame

class IngredientGameMealsFormatter(
    private val mealsRepository: MealsRepository
) {
    fun getFormatedMeals(): List<MealForIngredientGame> {
        val allMealsIngredients = mealsRepository.getAllMeals()
            .map { meal -> meal.ingredients }
            .flatten()
            .shuffled()
        return mealsRepository.getAllMeals()
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
                            .take(2).plus(currentFullMeal.ingredients[correctIngredientIndex]).shuffled(),
                )
            }
    }

}