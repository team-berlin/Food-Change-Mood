package org.berlin.logic

import org.berlin.model.MealForIngredientGame

class IngredientGameMealsFormatter(
    private val mealsRepository: MealsRepository
) {
    fun getFormatedMeals(): List<MealForIngredientGame> {
        val allMealsIngredients=mealsRepository.getAllMeals()
            .map { meal->meal.ingredients }
            .flatten()
            .shuffled()
        return mealsRepository.getAllMeals()
            .map { fullMeal ->

                val allMealsIngredientsExceptCurrentMeal=
                    allMealsIngredients.filterNot { it in fullMeal.ingredients }.shuffled()

                val correctIngredientIndex=(0..<fullMeal.nIngredients).random()

                MealForIngredientGame(
                    mealName = fullMeal.name,
                    correctIngredient = fullMeal.ingredients[correctIngredientIndex],
                    threeIngredientOnlyOneCorrect =
                        allMealsIngredientsExceptCurrentMeal
                        .take(2).plus(fullMeal.ingredients[correctIngredientIndex]).shuffled(),
                )
            }
    }

}