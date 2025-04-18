package logic.usecase

import org.berlin.logic.MealsRepository
import org.berlin.model.Meal
import org.berlin.model.SeafoodMeal

class GetSeafoodMealsUseCase(private val mealsRepository: MealsRepository) {
    companion object {
        const val MEAL_NAME = "seafood"
    }

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
        return input.tags.isNotEmpty() && input.tags.contains(MEAL_NAME)
    }
}