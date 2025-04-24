package common

import createMeal
import org.berlin.model.Meal

fun generateEasyMeals(count: Int): List<Meal> {
    return List(count){ index ->
        createMeal(
            id = index,
            name = "Easy Meal",
            minutes = index,
            numberOfIngredients = 3,
            numberOfStep = 5
        )
    }
    }

fun generateFoodCulture(count: Int): List<Meal> {
    return List(count) { index ->
        createMeal(
            name = "Meal $index",
            tags = listOf("Country $index"),
            description = "Description for country $index",
            steps = listOf("Step one for $index", "Step two for $index")
        )
    }
}