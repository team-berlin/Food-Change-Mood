package org.berlin.presentation.common

import org.berlin.model.Meal
import org.berlin.presentation.input_output.Viewer

fun showMealDetails(meal: Meal, viewer: Viewer) {
    viewer.display("Name: ${meal.name}")
    viewer.display("Description: ${meal.description ?: "No description provided."}")
    viewer.display("Preparation Time (minutes): ${meal.minutes}")
    viewer.display("Tags: ${meal.tags.joinToString(", ")}")

    with(meal.nutrition) {
        viewer.display("Nutrition:")
        viewer.display("  Calories: $calories kcal")
        viewer.display("  Total Fat: $totalFat g")
        viewer.display("  Saturated Fat: $saturatedFat g")
        viewer.display("  Carbohydrates: $carbohydrates g")
        viewer.display("  Sugar: $sugar g")
        viewer.display("  Protein: $protein g")
        viewer.display("  Sodium: $sodium mg")
        viewer.display("  Ingredients: ${meal.nIngredients}")
        viewer.display("  Steps: ${meal.nSteps}")
    }

    viewer.display("Steps:")
    meal.steps.forEachIndexed { index, step ->
        viewer.display("  ${index + 1}. $step")
    }

    viewer.display("Ingredients (${meal.nIngredients}): ${meal.ingredients.joinToString(", ")}")
}
