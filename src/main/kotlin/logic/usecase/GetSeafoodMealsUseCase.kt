package org.berlin.logic

import org.berlin.model.Meal
import org.berlin.model.SeafoodMeal

class GetSeafoodMealsUseCase(private val mealsRepository: MealsRepository) {
    fun getSeafoodMeals(): List<SeafoodMeal> {
        val seafoodList: MutableList<SeafoodMeal> = mutableListOf()
        mealsRepository.getAllMeals()
            .filter(::validateMealTags)
            .sortedByDescending { it.nutrition.protein }
            .forEach { seafoodMeal ->
                seafoodList.add(SeafoodMeal(seafoodMeal.name, seafoodMeal.nutrition.protein))
            }
        return seafoodList
    }

    private fun validateMealTags(input: Meal): Boolean {
        return input.tags.isNotEmpty() && input.tags.contains("seafood")
    }
}