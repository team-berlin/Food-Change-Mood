package org.berlin.logic

import org.berlin.model.Meal

class GetSeafoodMealsUseCase(private val mealsRepository: MealsRepository) {
    fun getSeafoodMeals(): List<Pair<Int, Pair< String, Double>>> {
        val seafoodMeals = mealsRepository.getAllMeals()
            .filter(::validateMealDescription)
            .filter { it.tags.contains("seafood") }
            .sortedByDescending { it.nutrition.protein }
            .map {seafoodMeal->
                seafoodMeal.name to seafoodMeal.nutrition.protein }

        val rank = 1..seafoodMeals.size
        return rank.zip(seafoodMeals)
    }
    private fun validateMealDescription(input: Meal): Boolean {
        return input.tags.isNotEmpty()
    }
}