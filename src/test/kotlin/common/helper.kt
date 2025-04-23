package common

import createMealList
import org.berlin.model.Meal

fun genarateEasyMeals(count: Int): List<Meal> {
    return List(count){ index ->
        createMealList(
            id = index,
            name = "Easy Meal",
            minutes = index,
            nIngredients = 3,
            nSteps = 5
        )
    }
}