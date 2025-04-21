package Utils
import org.berlin.model.Meal

class DisplayMeals  {

    private fun displayMeal(meal: Meal) {
        println(meal.name)
        println("    Preparation Time: ${meal.minutes} minutes")
        println("    Tags: ${meal.tags.joinToString(", ")}")
        println("    Nutrition:")
        println("      - Calories: ${meal.nutrition.calories}")
        println("      - Protein: ${meal.nutrition.protein}g")
        println("      - Total Fat: ${meal.nutrition.totalFat}g")
        println("      - Saturated Fat: ${meal.nutrition.saturatedFat}g")
        println("      - Carbohydrates: ${meal.nutrition.carbohydrates}g")
        println("      - Sugar: ${meal.nutrition.sugar}g")
        println("      - Sodium: ${meal.nutrition.sodium}mg")
        println("    Ingredients: ${meal.nIngredients}")
        println("    Steps: ${meal.nSteps}")
    }

   fun displayListOfMeals(meals: List<Meal>) {
        meals.forEachIndexed { index, meal ->
            println("\n[${index + 1}] ${meal.name}")
            displayMeal(meal)
        }
        println("\nTotal meals found: ${meals.size}")
    }
    }

