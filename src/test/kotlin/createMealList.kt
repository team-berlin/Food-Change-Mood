import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.berlin.model.Meal
import org.berlin.model.Nutrition

fun createMealList(
    id: Int,
    name: String,
    minutes: Int,
    nIngredients: Int,
    nSteps: Int,
    contributorId: Int = 123,
    submissionDate: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date ,
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
        nSteps = nSteps,
        steps = steps,
        description = description,
        ingredients = ingredients,
        nIngredients = nIngredients
    )
}
