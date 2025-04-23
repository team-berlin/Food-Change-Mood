import io.mockk.mockk
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.berlin.model.Meal
import org.berlin.model.Nutrition

fun createMeals(
    id: Int,
    name: String,
    minutes: Int,
    nIngredients: Int,
    nSteps: Int

) =  Meal(
    name = name,
    id = id,
    minutes = minutes,
    contributorId =  123,
    submissionDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date,
    tags = listOf("tag1", "tag2"),
    nutrition =  Nutrition(
        calories = 100.0,
        totalFat = 10.0,
        sugar = 10.0,
        sodium = 10.0,
        proteinGrams = 10.0,
        saturatedFat = 10.0,
        carbohydrates = 10.0
    ),
    nSteps = nSteps,
    steps = listOf("step1", "step2"),
    description =  "description"  ,
    ingredients =  listOf("ingredient1", "ingredient2"),
    nIngredients = nIngredients

)

fun createMeal(
    name: String,
    id: Int,
    minutes: Int = 30,
    contributorId: Int = 1,
    submissionDate: LocalDate =LocalDate(2025, 4, 23),
    tags: List<String> = emptyList(),
    ingredients: List<String> = emptyList(),
    description: String? = null,
    steps: List<String> = emptyList()
): Meal {
    return Meal(
        name = name,
        id = id,
        minutes = minutes,
        contributorId = contributorId,
        submissionDate = submissionDate,
        tags = tags,
        nutrition = mockk(),
        nSteps = steps.size,
        steps = steps,
        description = description,
        ingredients = ingredients,
        nIngredients = ingredients.size
    )
}