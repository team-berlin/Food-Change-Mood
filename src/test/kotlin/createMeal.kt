import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.berlin.model.Meal
import org.berlin.model.Nutrition

fun createMeal(
    id: Int = 1234,
    name: String = "Meal",
    minutes: Int = 30,
    numberOfIngredients: Int = 5,
    numberOfStep: Int = 5,
    contributorId: Int = 123,
    submissionDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    tags: List<String> = listOf("tag1", "tag2"),
    nutrition: Nutrition = Nutrition(
        calories = 100.0,
        totalFat = 10.0,
        sugar = 10.0,
        sodium = 10.0,
        proteinGrams = 10.0,
        saturatedFat = 10.0,
        carbohydrates = 10.0
    ),
    steps: List<String> = listOf("step1", "step2"),
    description: String = "description",
    ingredients: List<String> = listOf("ingredient1", "ingredient2")
): Meal {
    return Meal(
        id = id,
        name = name,
        minutes = minutes,
        contributorId = contributorId,
        submissionDate = submissionDate ,
        tags = tags,
        nutrition = nutrition,
        numberOfSteps = numberOfStep,
        steps = steps,
        description = description,
        ingredients = ingredients,
        numberOfIngredients = numberOfIngredients
    )
}
