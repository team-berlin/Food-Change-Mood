package data

import kotlinx.datetime.LocalDate
import org.berlin.model.Meal
import org.berlin.model.Nutrition

class MealsCsvParser {

    fun parseColumnsToMeal(line: Array<String>): Meal {

        if (line.size < 12) {
            throw IllegalArgumentException(
                "Malformed CSV row, expected 12 columns but got ${line.size}: ${line.joinToString()}"
            )
        }

        val name = line[ColumnIndex.NAME].trim().removeAllSpaces()
        val id = line[ColumnIndex.ID].trim().removeAllSpaces().toIntOrNull() ?: -1
        val minutes = line[ColumnIndex.MINUTES].trim().removeAllSpaces().toIntOrNull() ?: -1
        val contributorId = line[ColumnIndex.CONTRIBUTOR_ID].trim().removeAllSpaces().toIntOrNull() ?: -1
        val submittedRaw = line[ColumnIndex.SUBMISSION_DATE].trim().removeAllSpaces()
        val tagsRaw = line[ColumnIndex.TAGS].trim().removeAllSpaces()
        val nutritionRaw = line[ColumnIndex.NUTRITION].trim().removeAllSpaces()
        val nSteps = line[ColumnIndex.N_STEPS].trim().removeAllSpaces().toIntOrNull() ?: 0
        val stepsRaw = line[ColumnIndex.STEPS].trim().removeAllSpaces()
        val description = line.getOrNull(ColumnIndex.DESCRIPTION)?.removeAllSpaces()?.trim()?.takeIf { it.isNotBlank() }
        val ingredientsRaw = line[ColumnIndex.INGREDIENTS].trim().removeAllSpaces()
        val nIngredients = line[ColumnIndex.N_INGREDIENTS].trim().removeAllSpaces().toIntOrNull() ?: 0

        val submittedDate = LocalDate.parse(submittedRaw)

        val tags = parseStringList(tagsRaw)
        val steps = parseStringList(stepsRaw)
        val ingredients = parseStringList(ingredientsRaw)

        val nutrition = parseNutrition(nutritionRaw)

        return Meal(
            name = name,
            id = id,
            minutes = minutes,
            contributorId = contributorId,
            submissionDate = submittedDate,
            tags = tags,
            nutrition = nutrition,
            numberOfSteps = nSteps,
            steps = steps,
            description = description,
            ingredients = ingredients,
            numberOfIngredients = nIngredients
        )
    }

    private fun parseStringList(raw: String): List<String> {
        val trimmed = raw.removePrefix("[").removeSuffix("]").trim()
        if (trimmed.isEmpty()) {
            return emptyList()
        }
        return trimmed.split(",").map { it.trim().removeSurrounding("'").removeSurrounding("\"") }
            .filter { it.isNotBlank() }
    }

    private fun parseNutrition(raw: String): Nutrition {
        val trimmed = raw.removePrefix("[").removeSuffix("]").trim()
        val parts = trimmed.split(",").map { it.trim().toDoubleOrNull() ?: 0.0 }

        val calories = parts[NutritionIndex.CALORIES]
        val totalFat = parts[NutritionIndex.TOTAL_FAT]
        val sugar = parts[NutritionIndex.SUGAR]
        val sodium = parts[NutritionIndex.SODIUM]
        val protein = parts[NutritionIndex.PROTEIN_GRAMS]
        val saturatedFat = parts[NutritionIndex.SATURATED_FAT]
        val carbohydrates = parts[NutritionIndex.CARBOHYDRATES]

        return Nutrition(
            calories = calories,
            totalFat = totalFat,
            sugar = sugar,
            sodium = sodium,
            proteinGrams = protein,
            saturatedFat = saturatedFat,
            carbohydrates = carbohydrates
        )
    }

    private fun String.removeAllSpaces(): String {
        return this.replace(ALL_SPACES_VALUE, " ")
    }

    private companion object {
        val ALL_SPACES_VALUE = "\\s+".toRegex()
    }
}