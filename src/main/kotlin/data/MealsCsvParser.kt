package com.berlin.data

import org.berlin.model.Meal
import org.berlin.model.Nutrition
import kotlinx.datetime.LocalDate

class MealsCsvParser {

    fun parseColumnsToMeal(line: Array<String>): Meal {
        // check column number
        if (line.size < 12) {
            val expandedLine = Array(12) { i -> if (i < line.size) line[i] else "" }
            return parseLine(expandedLine)
        }
        return parseLine(line)
    }

    private fun parseLine(line: Array<String>): Meal {
        val name = line[ColumnIndex.NAME].trim()
        val id = line[ColumnIndex.ID].trim().toIntOrNull() ?: -1
        val minutes = line[ColumnIndex.MINUTES].trim().toIntOrNull() ?: -1
        val contributorId = line[ColumnIndex.CONTRIBUTOR_ID].trim().toIntOrNull() ?: -1
        val submittedRaw = line[ColumnIndex.SUBMISSION_DATE].trim()
        val tagsRaw = line[ColumnIndex.TAGS].trim()
        val nutritionRaw = line[ColumnIndex.NUTRITION].trim()
        val nSteps = line[ColumnIndex.N_STEPS].trim().toIntOrNull() ?: 0
        val stepsRaw = line[ColumnIndex.STEPS].trim()
        val description = line[ColumnIndex.DESCRIPTION].trim() ?: null
        val ingredientsRaw = line[ColumnIndex.INGREDIENTS].trim()
        val nIngredients = line[ColumnIndex.N_INGREDIENTS].trim().toIntOrNull() ?: 0

        val submittedDate = try {
            LocalDate.parse(submittedRaw)
        } catch (e: Exception) {
            // default value if date error
            LocalDate(2000, 1, 1)
        }

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
            nSteps = nSteps,
            steps = steps,
            description = description,
            ingredients = ingredients,
            nIngredients = nIngredients
        )
    }

    private fun parseStringList(raw: String): List<String> {
        val trimmed = raw.removePrefix("[").removeSuffix("]").trim()
        if (trimmed.isEmpty()) {
            return emptyList()
        }
        return trimmed
            .split(",")
            .map { it.trim().removeSurrounding("'").removeSurrounding("\"") }
            .filter { it.isNotBlank() }
    }

    private fun parseNutrition(raw: String): Nutrition {
        val trimmed = raw.removePrefix("[").removeSuffix("]").trim()
        val parts = trimmed.split(",").map { it.trim().toDoubleOrNull() ?: 0.0 }

        val paddedParts = if (parts.size < 7) {
            parts.toMutableList().apply {
                while (size < 7) add(0.0)
            }
        } else {
            parts
        }

        val calories = paddedParts[NutritionIndex.CALORIES]
        val totalFat = paddedParts[NutritionIndex.TOTAL_FAT]
        val sugar = paddedParts[NutritionIndex.SUGAR]
        val sodium = paddedParts[NutritionIndex.SODIUM]
        val protein = paddedParts[NutritionIndex.PROTEIN]
        val saturatedFat = paddedParts[NutritionIndex.SATURATED_FAT]
        val carbohydrates = paddedParts[NutritionIndex.CARBOHYDRATES]

        return Nutrition(
            calories = calories,
            totalFat = totalFat,
            sugar = sugar,
            sodium = sodium,
            protein = protein,
            saturatedFat = saturatedFat,
            carbohydrates = carbohydrates
        )
    }
}