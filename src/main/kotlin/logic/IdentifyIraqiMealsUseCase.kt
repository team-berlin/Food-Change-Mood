package org.berlin.logic

import org.berlin.model.Meal

class IdentifyIraqiMealsUseCase(
    private val repository: MealsRepository
) {

    val allMeals by lazy {repository.getAllMeals()}
    fun identifyIraqiMeals(): List<Meal> {
        return allMeals.filter {
            it.tags.any { tag -> tag.contains(IRAQI_TAG, ignoreCase = true) } ||
            it.description?.contains(IRAQI_TAG, ignoreCase = true) == true
        }
    }

    companion object {
        const val IRAQI_TAG = "iraqi"
    }


}