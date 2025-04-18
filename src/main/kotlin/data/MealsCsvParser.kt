package com.berlin.data

import org.berlin.model.Meal
import org.berlin.model.Nutrition
import kotlinx.datetime.LocalDate

class MealsCsvParser {

         fun parseColumnsToMeal(line: Array<String>): Meal {

             if (line.size < 12) {
                 throw IllegalArgumentException(
                     "Malformed CSV row, expected 12 columns but got ${line.size}: ${line.joinToString()}"
                 )
             }

            val name = line[ColumnIndex.NAME].trim()
            val id = line[ColumnIndex.ID].trim().toIntOrNull() ?: -1
            val minutes = line[ColumnIndex.MINUTES].trim().toIntOrNull() ?: -1
            val contributorId = line[ColumnIndex.CONTRIBUTOR_ID].trim().toIntOrNull() ?: -1
            val submittedRaw = line[ColumnIndex.SUBMISSION_DATE].trim()
            val tagsRaw = line[ColumnIndex.TAGS].trim()
            val nutritionRaw = line[ColumnIndex.NUTRITION].trim()
            val nSteps = line[ColumnIndex.N_STEPS].trim().toIntOrNull() ?: 0
            val stepsRaw = line[ColumnIndex.STEPS].trim()
            val description = line.getOrNull(ColumnIndex.DESCRIPTION)?.trim()?.takeIf { it.isNotBlank() }
            val ingredientsRaw = line[ColumnIndex.INGREDIENTS].trim()
            val nIngredients = line[ColumnIndex.N_INGREDIENTS].trim().toIntOrNull() ?: 0

            val submittedDate = LocalDate.parse(submittedRaw)

            val tags = parseStringList(tagsRaw)
            val steps = parseStringList(stepsRaw)
            val ingredients = parseStringList(ingredientsRaw)

            val nutrition   = parseNutrition(nutritionRaw)

            return Meal(
                name = name,
                id = id,
                minutes = minutes,
                contributorId = contributorId,
                submissionDate= submittedDate,
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

            val calories = parts[NutritionIndex.CALORIES]
            val totalFat = parts[NutritionIndex.TOTAL_FAT]
            val sugar = parts[NutritionIndex.SUGAR]
            val sodium = parts[NutritionIndex.SODIUM]
            val protein = parts[NutritionIndex.PROTEIN]
            val saturatedFat = parts[NutritionIndex.SATURATED_FAT]
            val carbohydrates = parts[NutritionIndex.CARBOHYDRATES]

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