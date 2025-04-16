package org.berlin.logic

import org.berlin.model.Meal

class IdentifyIraqiMealsUseCase(
    private val repository: MealsRepository
) {

    fun identifyIraqiMeals(): List<Meal> {
        return repository.getAllMeals().filter {
            it.tags.any { tag -> tag.equals(IRAQI_TAG, ignoreCase = true) } ||
            it.description?.contains(IRAQI_TAG, ignoreCase = true) == true
        }
    }

    companion object {
        const val IRAQI_TAG = "iraqi"
    }


}