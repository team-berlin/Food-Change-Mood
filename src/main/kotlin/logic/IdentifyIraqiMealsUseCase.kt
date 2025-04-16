package org.berlin.logic

import org.berlin.model.Meal

class IdentifyIraqiMealsUseCase(
    private val repository: MealsRepository
) {

    fun identifyIraqiMeals(): List<Meal> {
        return repository.getAllMeals().filter {
            it.tags.any { tag -> tag.equals("iraqi", ignoreCase = true) } ||
            it.description?.contains("iraqi", ignoreCase = true) == true
        }
    }

}